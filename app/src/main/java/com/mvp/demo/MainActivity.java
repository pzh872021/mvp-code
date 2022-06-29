package com.mvp.demo;

import android.os.Bundle;

import com.mvp.base.BasicActivity;
import com.mvp.base.XInjectPresenter;
import com.mvp.demo.databinding.ActivityMainBinding;

public class MainActivity extends BasicActivity<ActivityMainBinding> implements MainView {

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