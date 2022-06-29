package com.mvp.net.request;

import com.mvp.net.request.OkHttpRequestBuilder;

import java.util.Map;

public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, String> params);

    OkHttpRequestBuilder addParams(String key, String val);
}
