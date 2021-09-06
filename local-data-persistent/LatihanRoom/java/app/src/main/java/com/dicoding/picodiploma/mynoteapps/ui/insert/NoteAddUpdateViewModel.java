package com.dicoding.picodiploma.mynoteapps.ui.insert;

import android.app.Application;
import androidx.lifecycle.ViewModel;

import com.dicoding.picodiploma.mynoteapps.database.Note;
import com.dicoding.picodiploma.mynoteapps.repository.NoteRepository;

public class NoteAddUpdateViewModel extends ViewModel {

    private final NoteRepository mNoteRepository;

    public NoteAddUpdateViewModel(Application application) {
        mNoteRepository = new NoteRepository(application);
    }

    public void insert(Note note) {
        mNoteRepository.insert(note);
    }

    public void update(Note note) {
        mNoteRepository.update(note);
    }

    public void delete(Note note) {
        mNoteRepository.delete(note);
    }
}
