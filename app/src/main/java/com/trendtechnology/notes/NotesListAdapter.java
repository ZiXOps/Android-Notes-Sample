package com.trendtechnology.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trendtechnology.notes.model.Note;

import java.util.Collections;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.myViewHolder> {

    private LayoutInflater inflater;
    List<Note> noteList = Collections.emptyList();

    public NotesListAdapter(Context context, List<Note> noteList) {
        inflater = LayoutInflater.from(context);
        this.noteList = noteList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_item, parent);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Note current = noteList.get(position);
        holder.titleTextView.setText(current.getName());
        holder.noteTextView.setText(current.getText());
    }

    @Override
    public int getItemCount() {
        return 0;
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