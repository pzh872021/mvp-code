package com.mvp.demo.mvp;

import android.os.Bundle;
import android.view.View;

import com.mvp.base.BasicActivity;
import com.mvp.base.XInjectPresenter;
import com.mvp.demo.R;
import com.mvp.demo.databinding.ActivityMvpBinding;
import com.mvp.demo.mvp.presenter.GetCodePresenter;
import com.mvp.demo.mvp.presenter.LoginPresenter;
import com.mvp.demo.mvp.view.GetCodeView;
import com.mvp.demo.mvp.view.LoginView;

public class MvpActivity extends BasicActivity<ActivityMvpBinding>
        implements LoginView, GetCodeView {

    @XInjectPresenter
    GetCodePresenter mGetCodePresenter;

    @XInjectPresenter
    LoginPresenter mLoginPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mDataBinding.setGetCodeEnable(true);
        mDataBinding.setGetCodeTitle("获取验证码");
    }

    public void getCode(View view) {
        mGetCodePresenter.getCode();
    }

    public void login(View view) {
        String phone = mDataBinding.edPhone.getText().toString().trim();
        String code = mDataBinding.edCode.getText().toString().trim();
        mLoginPresenter.login(phone, code);
    }

    @Override
    public void changeGetCodeTitle(String title, boolean isLast) {
        mDataBinding.setGetCodeTitle(title);
        mDataBinding.setGetCodeEnable(isLast);
    }

    @Override
    public void cancelHandler() {
        super.cancelHandler();
        mGetCodePresenter.cancelDownCount();
    }

    @Override
    public void loginSuccess() {
        toastShort("登录成功");
        finish();
    }
}
