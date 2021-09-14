package com.dicoding.picodiploma.myasynctaskwithprogressbar;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    private static final String DEMO_ASYNC = "DemoAsyncWithProgress";

    private Button buttonStart;
    private ProgressBar progressBar;
    private DemoAsync demoAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        buttonStart = findViewById(R.id.btn_start);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pengecekan apakah demoasync null?
                if (demoAsync != null) {

                    // Ambil status dari async
                    AsyncTask.Status status = demoAsync.getStatus();

                    /*
                      Di sini dilakukan pengecekan status dari async
                      1. PENDING, berarti asynctask belum berjalan
                      2. RUNNING, berarti asynctask sedang berjalan
                      3. FINISHED, berarti asynctask sudah selesai
                     */

                    switch (status) {
                        case PENDING:
                            // Jika PENDING, maka langsung jalankan asynctask
                            demoAsync.execute();
                            break;
                        case RUNNING:
                            // Jika RUNNING, maka infokan untuk tunggu sampai selesai
                            Toast.makeText(MainActivity.this, "Async masih berjalan, silakan tunggu sampai selesai..", Toast.LENGTH_SHORT).show();
                            break;
                        case FINISHED:
                            // Jika FINISHED, maka buat asynctask baru kemudian jalankkan
                            // Perlu diingat bahwa asynctask bersifat fire & forget,
                            // Fire & forget membatasi execute asynctask yang kedua kalinya, oleh karena itu perlu objek baru
                            demoAsync = new DemoAsync(MainActivity.this);
                            demoAsync.execute();
                            break;
                    }
                } else {

                    // Buat async baru
                    demoAsync = new DemoAsync(MainActivity.this);

                    // Execute asynctask dengan parameter string 'Halo Ini Demo AsyncTask'
                    demoAsync.execute();
                }
            }
        });
    }

    @Override
    public void onPreExecute() {

        // Reset progress dari 0
        progressBar.setProgress(0);

    }

    @Override
    public void onUpdateProgress(long value) {

        // Maksimum nilai dari progress nya 10000
        final double MAX_PROGRESS = 10000.0;

            /*
            Karena maksimal nilai pada view ProgressBar adalah 100,
            maka kita harus mengkonversi value ke dalam skala 100
            */

        double progress = 100 * (value / MAX_PROGRESS);

        progressBar.setProgress((int) progress);
    }

    @Override
    public void onPostExecute(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 3 parameter generic <String, Void, String>
     * 1. Params, parameter input yang bisa dikirimkan
     * 2. Progress, digunakan untuk publish informasi sudah sampai mana proses background berjalan
     * 3. Result, object yang dikirimkan ke onPostExecute / hasil dari proses doInBackground
     */

    /*
    Perhatikan pada params ke-2 yaitu dengan menggunakan Long
    Dan params 1 dan 3 kita set Void
    */
    private static class DemoAsync extends AsyncTask<Void, Long, Void> {

        // Penggunaan weakreference disarankan untuk menghindari memory leaks
        final WeakReference<MyAsyncCallback> myListener;

        DemoAsync(MyAsyncCallback myListener) {

            this.myListener = new WeakReference<>(myListener);

        }

        /*
        onPreExecute digunakan untuk persiapan asynctask
        berjalan di Main Thread, bisa akses ke view karena masih di dalam Main Thread
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.myListener.get().onPreExecute();
        }

        /*
        doInBackground digunakan untuk menjalankan proses secara async
        berjalan di background thread, tidak bisa akses ke view karena sudah beda thread
         */
        @Override
        protected Void doInBackground(Void... params) {

            // 2000 miliseconds = 2 detik
            long waitingTime = 2000;
            long startingTime = 0;

            for (int x = 0; x < 5; x++) {
                try {

                    Thread.sleep(waitingTime);

                    // Update progress dengan memanggil publishProgress
                    publishProgress(startingTime += waitingTime);

                } catch (Exception e) {
                    Log.d(DEMO_ASYNC, e.getMessage());
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);

            long value = values[0];

            this.myListener.get().onUpdateProgress(value);
        }

        /*
        onPostExecute dijalankan ketika proses doInBackground telah selesai
        berjalan di Main Thread
        */
        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            this.myListener.get().onPostExecute("Finish");
        }
    }
}
