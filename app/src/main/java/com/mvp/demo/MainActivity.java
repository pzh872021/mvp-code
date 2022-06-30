package com.mvp.demo;

import android.os.Bundle;
import android.view.View;

import com.mvp.base.BasicActivity;
import com.mvp.demo.databinding.ActivityMainBinding;
import com.mvp.demo.mvp.MvpActivity;
import com.mvp.kit.Kits;
import com.mvp.kit.Logger;
import com.mvp.kit.Router;

public class MainActivity extends BasicActivity<ActivityMainBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mDataBinding.setMainHandler(new MainHandler());
    }

    public class MainHandler {

        public void mvpDemo(View view) {
            Router.newIntent(getAtyContext())
                    .to(MvpActivity.class)
                    .launch();
        }

        public void recyclerViewDemo(View view) {

        }
    }
}