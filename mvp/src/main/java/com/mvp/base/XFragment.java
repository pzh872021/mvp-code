package com.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

abstract class XFragment<B extends ViewDataBinding> extends Fragment implements XView {

    protected B dataBinding;
    private XProxyActivity<XView> mProxyActivity;
    private boolean isViewCreate = false;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (dataBinding != null) {
            ViewGroup parent = (ViewGroup) dataBinding.getRoot().getParent();
            if (null != parent) {
                parent.removeView(dataBinding.getRoot());
            }
        } else {
            if (!isViewCreate) {
                dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
                mProxyActivity = createProxyActivity();
                mProxyActivity.bindPresenter();
                beforeCreateView(savedInstanceState);
                initViews(savedInstanceState);
                afterCreateView(savedInstanceState);
                isViewCreate = true;
            }
        }
        return dataBinding.getRoot();
    }


    private XProxyActivity<XView> createProxyActivity() {
        if (mProxyActivity == null) {
            mProxyActivity = new XProxyActivity<>(this);
        }
        return mProxyActivity;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isViewCreate) {
                onPagerResume();
            }
        }
    }

    /**
     * 在ViewPager使用的Fragment的onResume方法
     */
    public void onPagerResume() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (isViewCreate) {
                onFragmentResume();
            }
        }
    }

    /**
     * 单独使用的Fragment的onResume方法
     */
    public void onFragmentResume() {

    }


    @Override
    public void cancelHandler() {

    }

    @Override
    public void beforeCreateView(Bundle savedInstanceState) {

    }


    @Override
    public void afterCreateView(Bundle savedInstanceState) {

    }

    @Override
    public Activity getAtyContext() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxyActivity.unbindPresenter();
    }

    @Override
    public void toastLong(String msg) {
        Toast.makeText(getAtyContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void toastShort(String msg) {
        Toast.makeText(getAtyContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String msg) {

    }
}
