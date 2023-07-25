package com.dicoding.picodiploma.myworkmanager;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOneTimeTask, btnPeriodicTask, btnCancelTask;
    EditText editCity;
    TextView textStatus;
    private WorkManager workManager;
    private PeriodicWorkRequest periodicWorkRequest;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }

        btnOneTimeTask = findViewById(R.id.btnOneTimeTask);
        editCity = findViewById(R.id.editCity);
        textStatus = findViewById(R.id.textStatus);
        btnPeriodicTask = findViewById(R.id.btnPeriodicTask);
        btnCancelTask = findViewById(R.id.btnCancelTask);

        workManager = WorkManager.getInstance(this);

        btnOneTimeTask.setOnClickListener(this);
        btnPeriodicTask.setOnClickListener(this);
        btnCancelTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnOneTimeTask) {
            startOneTimeTask();
        } else if (id == R.id.btnPeriodicTask) {
            startPeriodicTask();
        } else if (id == R.id.btnCancelTask) {
            cancelPeriodicTask();
        }
    }

    private void startOneTimeTask() {
        textStatus.setText(getString(R.string.status));

        Data data = new Data.Builder()
                .putString(MyWorker.EXTRA_CITY, editCity.getText().toString())
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        workManager.enqueue(oneTimeWorkRequest);

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(MainActivity.this, workInfo -> {
                    String status = workInfo.getState().name();
                    textStatus.append("\n"+status);
                });
    }

    private void startPeriodicTask() {
        textStatus.setText(getString(R.string.status));

        Data data = new Data.Builder()
                .putString(MyWorker.EXTRA_CITY, editCity.getText().toString())
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        workManager.enqueue(periodicWorkRequest);

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(MainActivity.this, workInfo -> {
                    String status = workInfo.getState().name();
                    textStatus.append("\n"+status);
                    btnCancelTask.setEnabled(false);
                    if (workInfo.getState() == WorkInfo.State.ENQUEUED){
                        btnCancelTask.setEnabled(true);
                    }
                });
    }

    private void cancelPeriodicTask() {
        workManager.cancelWorkById(periodicWorkRequest.getId());
    }
}