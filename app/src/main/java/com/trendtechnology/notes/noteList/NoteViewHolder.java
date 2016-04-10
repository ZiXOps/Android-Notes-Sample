package com.trendtechnology.notes.noteList;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public NoteViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

}
