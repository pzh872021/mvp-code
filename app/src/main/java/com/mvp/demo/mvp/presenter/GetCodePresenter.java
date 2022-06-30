package com.mvp.demo.mvp.presenter;

import android.os.Handler;

import com.mvp.base.BasicPresenter;
import com.mvp.demo.mvp.view.GetCodeView;

public class GetCodePresenter extends BasicPresenter<GetCodeView> {
    private int time = 10;
    private final Handler handler = new Handler();
    private final Runnable getCodeRunnable = new Runnable() {
        @Override
        public void run() {
            time--;
            if (time == 0) {
                handler.removeCallbacks(getCodeRunnable);
                mProxyView.changeGetCodeTitle("重新获取", true);
                time = 10;
                return;
            }
            mProxyView.changeGetCodeTitle(time + "秒后重新获取", true);
            handler.postDelayed(getCodeRunnable, 1000);
        }
    };

    public void getCode() {
        handler.post(getCodeRunnable);
    }

    public void cancelDownCount() {
        handler.removeCallbacks(getCodeRunnable);
    }
}
