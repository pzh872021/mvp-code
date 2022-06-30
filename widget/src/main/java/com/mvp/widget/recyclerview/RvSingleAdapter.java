package com.mvp.widget.recyclerview;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

public abstract class RvSingleAdapter<B extends ViewDataBinding> extends RvBasicAdapter {

    public RvSingleAdapter(Context context) {
        super(context);
        addDelegate(new RvItemView<B>() {
            @Override
            public int getItemLayoutId() {
                return getLayoutId();
            }

            @Override
            public boolean isViewType(int position) {
                return true;
            }

            @Override
            public void bindingView(B viewDataBinding, int position) {
                bingView(viewDataBinding, position);
            }
        });
    }

    public abstract int getLayoutId();

    public abstract void bingView(B viewDataBinding, int position);
}
