package com.mvp.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

abstract class XActivity<B extends ViewDataBinding> extends AppCompatActivity implements XView {

    private XProxyActivity<XView> mProxyActivity;
    protected B mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeCreateView(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mProxyActivity = createProxyActivity();
        mProxyActivity.bindPresenter();
        initViews(savedInstanceState);
        afterCreateView(savedInstanceState);
    }

    private XProxyActivity<XView> createProxyActivity() {
        if (mProxyActivity == null) {
            return new XProxyActivity<>(this);
        }
        return mProxyActivity;
    }

    @Override
    public Activity getAtyContext() {
        return this;
    }

    @Override
    public void beforeCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void afterCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void cancelNetWork() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxyActivity.unbindPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int index = 0; index < fragmentManager.getFragments().size(); index++) {
            Fragment fragment = fragmentManager.getFragments().get(index);
            if (fragment != null) {
                handleResult(fragment, requestCode, resultCode, data);
            }
        }
    }

    /**
     * 统一处理Fragment没法走onActivityResult的原生Bug
     *
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments();
        for (Fragment f : childFragment) {
            if (f != null) {
                handleResult(f, requestCode, resultCode, data);
            }
        }
    }
}
