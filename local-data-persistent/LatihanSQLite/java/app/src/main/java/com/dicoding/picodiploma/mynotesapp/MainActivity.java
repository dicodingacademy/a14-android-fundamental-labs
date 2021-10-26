package com.dicoding.picodiploma.mynotesapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.mynotesapp.adapter.NoteAdapter;
import com.dicoding.picodiploma.mynotesapp.db.NoteHelper;
import com.dicoding.picodiploma.mynotesapp.entity.Note;
import com.dicoding.picodiploma.mynotesapp.helper.MappingHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LoadNotesCallback {
    private ProgressBar progressBar;
    private RecyclerView rvNotes;
    private NoteAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    // Akan dipanggil jika request codenya ADD
                    if (result.getResultCode() == NoteAddUpdateActivity.RESULT_ADD) {
                        Note note = result.getData().getParcelableExtra(NoteAddUpdateActivity.EXTRA_NOTE);

                        adapter.addItem(note);
                        rvNotes.smoothScrollToPosition(adapter.getItemCount() - 1);

                        showSnackbarMessage("Satu item berhasil ditambahkan");
                    }
                    /*
                    Akan dipanggil jika result codenya  UPDATE
                    Semua data di load kembali dari awal
                    */
                    else if (result.getResultCode() == NoteAddUpdateActivity.RESULT_UPDATE) {

                        Note note = result.getData().getParcelableExtra(NoteAddUpdateActivity.EXTRA_NOTE);
                        int position = result.getData().getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0);

                        adapter.updateItem(position, note);
                        rvNotes.smoothScrollToPosition(position);

                        showSnackbarMessage("Satu item berhasil diubah");
                    }
                    /*
                    Akan dipanggil jika result codenya DELETE
                    Delete akan menghapus data dari list berdasarkan dari position
                    */
                    else if (result.getResultCode() == NoteAddUpdateActivity.RESULT_DELETE) {
                        int position = result.getData().getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0);

                        adapter.removeItem(position);

                        showSnackbarMessage("Satu item berhasil dihapus");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Notes");

        progressBar = findViewById(R.id.progressbar);
        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        adapter = new NoteAdapter(new NoteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Note selectedNote, Integer position) {
                Intent intent = new Intent(MainActivity.this, NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position);
                resultLauncher.launch(intent);
            }
        });
        rvNotes.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NoteAddUpdateActivity.class);
            resultLauncher.launch(intent);
        });

        if (savedInstanceState == null) {
            new LoadNotesAsync(this, this).execute();
        } else {
            ArrayList<Note> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListNotes(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListNotes());
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Note> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListNotes(notes);
        } else {
            adapter.setListNotes(new ArrayList<>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadNotesAsync {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNotesAsync(Context context, LoadNotesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            weakCallback.get().preExecute();
            executor.execute(() -> {
                Context context = weakContext.get();

                NoteHelper noteHelper = NoteHelper.getInstance(context);
                noteHelper.open();
                Cursor dataCursor = noteHelper.queryAll();
                ArrayList<Note> notes = MappingHelper.mapCursorToArrayList(dataCursor);
                noteHelper.close();

                handler.post(() -> weakCallback.get().postExecute(notes));
            });
        }

    }

    /**
     * Tampilkan snackbar
     *
     * @param message inputan message
     */
    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }
}

interface LoadNotesCallback {
    void preExecute();

    void postExecute(ArrayList<Note> notes);
}