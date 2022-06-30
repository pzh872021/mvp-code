package com.mvp.widget.recyclerview;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class RvViewHolder extends RecyclerView.ViewHolder {

    public final ViewDataBinding viewDataBinding;

    public RvViewHolder(@NonNull ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.viewDataBinding =  viewDataBinding;
    }
}
