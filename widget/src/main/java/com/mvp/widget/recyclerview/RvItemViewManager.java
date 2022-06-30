package com.mvp.widget.recyclerview;

import androidx.collection.SparseArrayCompat;
import androidx.databinding.ViewDataBinding;

public class RvItemViewManager {

    private SparseArrayCompat<RvItemView> delegates = new SparseArrayCompat();

    public int getDelegateCount() {
        return delegates.size();
    }

    public RvItemViewManager addDelegate(RvItemView delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
        }
        return this;
    }

    public int getItemViewType(int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            RvItemView delegate = delegates.valueAt(i);
            if (delegate.isViewType(position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException("item =  " + position + " has not layout id");
    }

    public <B extends ViewDataBinding> void convert(B viewDataBinding, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            RvItemView<B> delegate = delegates.valueAt(i);
            if (delegate.isViewType(position)) {
                delegate.bindingView(viewDataBinding, position);
                return;
            }
        }
        throw new IllegalArgumentException("item = " + position + " has not layout id");
    }

    public RvItemView getDelegate(int viewType) {
        return delegates.get(viewType);
    }
}
