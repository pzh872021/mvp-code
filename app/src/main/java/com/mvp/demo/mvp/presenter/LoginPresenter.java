package com.mvp.demo.mvp.presenter;

import android.text.TextUtils;

import com.mvp.base.BasicPresenter;
import com.mvp.demo.mvp.view.LoginView;

public class LoginPresenter extends BasicPresenter<LoginView> {

    public void login(String phone, String code) {
        if (TextUtils.isEmpty(phone)) {
            mProxyView.toastShort("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mProxyView.toastShort("验证码不能为空");
            return;
        }
        mProxyView.loginSuccess();
    }
}
