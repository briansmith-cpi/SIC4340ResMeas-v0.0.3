package com.sic.resmeasure.widgets.holders;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sic.resmeasure.R;
import com.sic.resmeasure.binders.models.LogViewHolderModel;
import com.sic.resmeasure.databinding.ViewHolderLogBinding;


public class LogViewHolder extends RecyclerView.ViewHolder {

    public static LogViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        ViewHolderLogBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_holder_log, parent, false);
        return new LogViewHolder(binding);
    }

    private ViewHolderLogBinding binding;

    private LogViewHolder(ViewHolderLogBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(LogViewHolderModel model) {
        binding.setModel(model);
        binding.executePendingBindings();
    }

    public void setBackgroundColor(int position) {
        int backgroundColor;
        if (position % 2 != 0) {
            backgroundColor = getBackgroundColor(R.color.grey_200);
        } else {
            backgroundColor = getBackgroundColor(R.color.grey_100);
        }
        itemView.setBackgroundColor(backgroundColor);
    }

    private int getBackgroundColor(int resId) {
        int backgroundColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            backgroundColor = itemView.getResources().getColor(resId, itemView.getContext().getTheme());
        } else {
            backgroundColor = itemView.getResources().getColor(resId);
        }
        return backgroundColor;
    }
}