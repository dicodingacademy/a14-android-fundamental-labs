package com.dicoding.picodiploma.mybroadcastreceiver;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;


public class DownloadService extends JobIntentService {

    public static final String TAG = DownloadService.class.getSimpleName();

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null) {
            enqueueWork(this, this.getClass(), 101, intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "Download Service dijalankan");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent notifyFinishIntent = new Intent(MainActivity.ACTION_DOWNLOAD_STATUS);
        sendBroadcast(notifyFinishIntent);
    }
}
