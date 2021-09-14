package com.dicoding.picodiploma.myasynctaskwithprogressbar

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val DEMO_ASYNC = "DemoAsyncWithProgress"
        // Maksimum nilai dari progress nya 10000
        private const val MAX_PROGRESS = 10000.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            progress_bar.progress = 0
            Toast.makeText(this, "Async masih berjalan, silakan tunggu sampai selesai..", Toast.LENGTH_SHORT).show()
            GlobalScope.launch{
                doInBackground()
            }
        }
    }

    private suspend fun doInBackground() {
        // 2000 miliseconds = 2 detik
        val waitingTime: Long = 2000
        var startingTime: Long = 0
        for (x in 0..4) {
            try {
                delay(waitingTime)

                // Update progress dengan memanggil publishProgress
                startingTime += waitingTime
                runOnUiThread {
                    publishProgress(startingTime)
                }
                Log.d(DEMO_ASYNC, startingTime.toString())
            } catch (e: Exception) {
                Log.d(DEMO_ASYNC, e.message.toString())
            }
        }
        runOnUiThread {
            Toast.makeText(this@MainActivity, "Finish", Toast.LENGTH_SHORT).show()
        }
    }

    private fun publishProgress(value: Long) {
        /*
            Karena maksimal nilai pada view progress_bar adalah 100,
            maka kita harus mengkonversi value ke dalam skala 100
            */
        val progress = 100 * (value / MAX_PROGRESS)
        progress_bar.progress = progress.toInt()
    }
}
