package com.mvp.base;

import android.app.Activity;
import android.os.Bundle;

public interface XView {
    /**
     * 获取UI的上下文
     *
     * @return
     */
    Activity getAtyContext();

    /**
     * 离开UI时取消网络，防止内存泄露
     */
    void cancelNetWork();

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
}
