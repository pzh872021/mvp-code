package com.mvp.net;

import com.mvp.net.callback.Callback;
import com.mvp.net.content.PostStringBuilder;
import com.mvp.net.file.PostFileBuilder;
import com.mvp.net.get.GetBuilder;
import com.mvp.net.get.HeadBuilder;
import com.mvp.net.other.OtherRequestBuilder;
import com.mvp.net.post.PostFormBuilder;
import com.mvp.net.other.OtherMethod;
import com.mvp.net.request.Platform;
import com.mvp.net.request.RequestCall;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OKUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OKUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    public OKUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }

    public static OKUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OKUtils.class) {
                if (mInstance == null) {
                    mInstance = new OKUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OKUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(OtherMethod.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(OtherMethod.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(OtherMethod.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (call.isCanceled()) {
                    sendCancelCallback(finalCallback, id);
                    return;
                }
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendCancelCallback(finalCallback, id);
                        return;
                    }
                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call,
                                new IOException("request failed , reponse's code is : " + response.code()),
                                finalCallback, id);
                        return;
                    }
                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }

    public void sendCancelCallback(final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(() -> {
            callback.onAfter(id);
            callback.onCancle();
        });
    }

    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(() -> {
            callback.onAfter(id);
            callback.onError(call, e, id);
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(() -> {
            callback.onAfter(id);
            callback.onResponse(object, id);
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

}

