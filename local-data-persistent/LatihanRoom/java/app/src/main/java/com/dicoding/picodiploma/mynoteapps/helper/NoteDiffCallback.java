package com.dicoding.picodiploma.mynoteapps.helper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.dicoding.picodiploma.mynoteapps.database.Note;

import java.util.List;

public class NoteDiffCallback extends DiffUtil.Callback {

    private final List<Note> oldNoteList;
    private final List<Note> newNoteList;

    public NoteDiffCallback(List<Note> oldNoteList, List<Note> newNoteList) {
        this.oldNoteList = oldNoteList;
        this.newNoteList = newNoteList;
    }

    @Override
    public int getOldListSize() {
        return oldNoteList.size();
    }

    @Override
    public int getNewListSize() {
        return newNoteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNoteList.get(oldItemPosition).getId() == newNoteList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Note oldNote = oldNoteList.get(oldItemPosition);
        final Note newNote = newNoteList.get(newItemPosition);

        return oldNote.getTitle().equals(newNote.getTitle()) && oldNote.getDescription().equals(newNote.getDescription());
    }
}