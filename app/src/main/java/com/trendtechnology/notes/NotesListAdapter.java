package com.trendtechnology.notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trendtechnology.notes.model.Note;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<Note> noteList;

    public NotesListAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.getBinding().setVariable(com.trendtechnology.notes.BR.note, note);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

}