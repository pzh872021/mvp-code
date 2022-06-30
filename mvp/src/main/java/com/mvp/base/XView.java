package com.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public interface XView {
    /**
     * 获取UI的上下文
     *
     * @return
     */
    Activity getAtyContext();

    /**
     * 销毁页面时的操作，比如取消网络请求，取消handler
     */
    void cancelHandler();

    /**
     * 设置布局之前，需要处理的逻辑。由子类根据实际需要复写
     *
     * @param savedInstanceState
     */
    void beforeCreateView(Bundle savedInstanceState);

    /**
     * 布局UI
     *
     * @return
     */
    int getLayoutId();

    /**
     * 处理UI视图
     *
     * @param savedInstanceState
     */
    void initViews(Bundle savedInstanceState);

    /**
     * 设置UI之后，需要处理的逻辑，由子类根据实际需要复写
     *
     * @param savedInstanceState
     */
    void afterCreateView(Bundle savedInstanceState);

    void toastShort(String msg);

    void toastLong(String msg);

    void showLoading(String msg);
}
