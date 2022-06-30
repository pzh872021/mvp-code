package com.mvp.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RvBasicAdapter extends RecyclerView.Adapter<RvViewHolder> {

    protected Context mContext;
    protected RvItemViewManager mRvItemViewManager;

    public RvBasicAdapter(Context context) {
        mContext = context;
        mRvItemViewManager = new RvItemViewManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (mRvItemViewManager.getDelegateCount() == 0) {
            return super.getItemViewType(position);
        }
        return mRvItemViewManager.getItemViewType(position);
    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemView rvItemView = mRvItemViewManager.getDelegate(viewType);
        int layoutId = rvItemView.getItemLayoutId();
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                layoutId, parent, false);
        return new RvViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        mRvItemViewManager.convert(holder.viewDataBinding, holder.getAdapterPosition());
        holder.viewDataBinding.executePendingBindings();
    }

    public <B extends ViewDataBinding> void addDelegate(RvItemView<B> rvItemView) {
        mRvItemViewManager.addDelegate(rvItemView);
    }

}
