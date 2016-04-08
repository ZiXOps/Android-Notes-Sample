package com.trendtechnology.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trendtechnology.notes.model.Note;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.myViewHolder> {

    List<Note> noteList;

    public NotesListAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Note current = noteList.get(position);
        holder.titleTextView.setText(current.getName());
        holder.noteTextView.setText(current.getText());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView noteTextView;

        public myViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleLabel);
            noteTextView = (TextView) itemView.findViewById(R.id.noteLabel);
        }
    }
}