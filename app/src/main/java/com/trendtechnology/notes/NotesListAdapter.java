package com.trendtechnology.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.myViewHolder> {

    private LayoutInflater inflater;

    public NotesListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_item, parent);
        return null;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        public myViewHolder(View itemView) {
            super(itemView);
        }
    }
}