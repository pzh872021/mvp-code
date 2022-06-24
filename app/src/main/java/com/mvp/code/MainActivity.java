package com.mvp.code;

import android.os.Bundle;

import com.mvp.base.BasicActivity;
import com.mvp.base.XInjectPresenter;
import com.mvp.code.databinding.ActivityMainBinding;

public class MainActivity extends BasicActivity<ActivityMainBinding> {

    @XInjectPresenter
    MainPresenter mMainPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }
}