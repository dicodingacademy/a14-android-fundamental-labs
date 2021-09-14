package com.dicoding.picodiploma.myserviceapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

    companion object {
        private val TAG = MyBoundService::class.java.simpleName
    }
    private var mBinder = MyBinder()
    private val startTime = System.currentTimeMillis()

    /*
    Countdown timer akan berjalan sampai 100000 milisecond,
    dengan interval setiap 1000 milisecond akan menampilkan log
     */
    private var mTimer: CountDownTimer = object : CountDownTimer(100000, 1000) {
        override fun onTick(l: Long) {
            val elapsedTime = System.currentTimeMillis() - startTime
            Log.d(TAG, "onTick: $elapsedTime")
        }

        override fun onFinish() {

        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    /*
    Method yang akan dipanggil ketika service diikatkan ke activity
     */
    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind: ")
        mTimer.start()
        return mBinder
    }

    /*
    Ketika semua ikatan sudah dilepas maka ondestroy akan secara otomatis dipanggil
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    /*
    Method yang akan dipanggil ketika service dilepas dari activity
     */
    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind: ")
        mTimer.cancel()
        return super.onUnbind(intent)
    }

    /*
    Method yang akan dipanggil ketika service diikatkan kembali
     */
    override fun onRebind(intent: Intent) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }

    internal inner class MyBinder : Binder() {
        val getService: MyBoundService = this@MyBoundService
    }
}
