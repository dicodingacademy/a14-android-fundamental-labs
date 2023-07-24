package com.dicoding.picodiploma.myalarmmanager;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.picodiploma.myalarmmanager.utils.DatePickerFragment;
import com.dicoding.picodiploma.myalarmmanager.utils.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private AlarmReceiver alarmReceiver;
    private TextView tvOnceDate;
    private TextView tvOnceTime;
    private EditText edtOnceMessage;

    private TextView tvRepeatingTime;
    private EditText edtRepeatingMessage;

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

        // Inisiasi view untuk one time alarm
        ImageButton btnOnceDate = findViewById(R.id.btn_once_date);
        ImageButton btnOnceTime = findViewById(R.id.btn_once_time);
        Button btnSetOnce = findViewById(R.id.btn_set_once_alarm);
        tvOnceDate = findViewById(R.id.tv_once_date);
        tvOnceTime = findViewById(R.id.tv_once_time);
        edtOnceMessage = findViewById(R.id.edt_once_message);

        // Listener one time alarm
        btnOnceDate.setOnClickListener(this);
        btnOnceTime.setOnClickListener(this);
        btnSetOnce.setOnClickListener(this);

        // Inisiasi view untuk repeating alarm
        ImageButton btnRepeatingTime = findViewById(R.id.btn_repeating_time);
        Button btnSetRepeating = findViewById(R.id.btn_set_repeating_alarm);
        Button btnCancelRepeating = findViewById(R.id.btn_cancel_repeating_alarm);
        tvRepeatingTime = findViewById(R.id.tv_repeating_time);
        edtRepeatingMessage = findViewById(R.id.edt_repeating_message);

        // Listener repeating alarm
        btnRepeatingTime.setOnClickListener(this);
        btnSetRepeating.setOnClickListener(this);
        btnCancelRepeating.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();
    }

    private final static String DATE_PICKER_TAG = "DatePicker";
    private final static String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    private final static String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_once_date) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        } else if (v.getId() == R.id.btn_once_time) {
            TimePickerFragment timePickerFragmentOne = new TimePickerFragment();
            timePickerFragmentOne.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
        } else if (v.getId() == R.id.btn_set_once_alarm) {
            String onceDate = tvOnceDate.getText().toString();
            String onceTime = tvOnceTime.getText().toString();
            String onceMessage = edtOnceMessage.getText().toString();

            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    onceDate,
                    onceTime,
                    onceMessage);
        } else if (v.getId() == R.id.btn_repeating_time) {
            TimePickerFragment timePickerFragmentRepeat = new TimePickerFragment();
            timePickerFragmentRepeat.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
        } else if (v.getId() == R.id.btn_set_repeating_alarm) {
            String repeatTime = tvRepeatingTime.getText().toString();
            String repeatMessage = edtRepeatingMessage.getText().toString();
            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    repeatTime, repeatMessage);
        } else if (v.getId() == R.id.btn_cancel_repeating_alarm) {
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
        }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {

        // Siapkan date formatter-nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Set text dari textview once
        tvOnceDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {

        // Siapkan time formatter-nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Set text dari textview berdasarkan tag
        switch (tag) {
            case TIME_PICKER_ONCE_TAG:
                tvOnceTime.setText(dateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                tvRepeatingTime.setText(dateFormat.format(calendar.getTime()));
                break;
            default:
                break;
        }
    }
}
