package com.mvp.widget.recyclerview;

import androidx.databinding.ViewDataBinding;

public interface RvItemView<B extends ViewDataBinding> {
    /**
     * 行布局
     *
     * @return
     */
    int getItemLayoutId();

    /**
     * 显示该布局对应的行的条件
     *
     * @param position
     * @return
     */
    boolean isViewType(int position);

    void bindingView(B viewDataBinding, int position);
}
