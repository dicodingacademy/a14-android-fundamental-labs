package com.dicoding.picodiploma.myreadwritefile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editContent;
    private EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNew = findViewById(R.id.button_new);
        Button btnOpen = findViewById(R.id.button_open);
        Button btnSave = findViewById(R.id.button_save);
        editContent = findViewById(R.id.edit_file);
        editTitle = findViewById(R.id.edit_title);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_new) {
            newFile();
        } else if (id == R.id.button_open) {
            showList();
        } else if (id == R.id.button_save) {
            saveFile();
        }
    }

    /**
     * Clear semua data yang sudah ditampilkan
     */
    private void newFile() {
        editTitle.setText("");
        editContent.setText("");
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method untuk menampilkan semua file yang ada
     */
    private void showList() {
        final CharSequence[] items = fileList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, (dialog, item) -> loadData(items[item].toString()));
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        editTitle.setText(fileModel.getFilename());
        editContent.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFilename() + " data", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method untuk save data, nama file akan diambil dari editTitle
     */
    private void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (editContent.getText().toString().isEmpty()) {
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }  else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving " + fileModel.getFilename()  + " file", Toast.LENGTH_SHORT).show();
        }
    }
}