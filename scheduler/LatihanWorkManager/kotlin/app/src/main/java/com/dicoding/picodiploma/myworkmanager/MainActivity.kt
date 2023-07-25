package com.dicoding.picodiploma.myworkmanager

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import androidx.work.Data.Builder
import com.dicoding.picodiploma.myworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        workManager = WorkManager.getInstance(this)

        binding.btnOneTimeTask.setOnClickListener(this)
        binding.btnPeriodicTask.setOnClickListener(this)
        binding.btnCancelTask.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnOneTimeTask -> startOneTimeTask()
            R.id.btnPeriodicTask -> startPeriodicTask()
            R.id.btnCancelTask -> cancelPeriodicTask()
        }
    }

    private fun startOneTimeTask() {
        binding.textStatus.text = getString(R.string.status)
        val data = Builder()
                .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
                .build()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInputData(data)
                .setConstraints(constraints)
                .build()
        workManager.enqueue(oneTimeWorkRequest)
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                .observe(this@MainActivity) { workInfo ->
                    val status = workInfo.state.name
                    binding.textStatus.append("\n" + status)
                }
    }

    private fun startPeriodicTask() {
        binding.textStatus.text = getString(R.string.status)
        val data = Builder()
                .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
                .build()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build()
        workManager.enqueue(periodicWorkRequest)
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
                .observe(this@MainActivity) { workInfo ->
                    val status = workInfo.state.name
                    binding.textStatus.append("\n" + status)
                    binding.btnCancelTask.isEnabled = false
                    if (workInfo.state == WorkInfo.State.ENQUEUED) {
                        binding.btnCancelTask.isEnabled = true
                    }
                }
    }

    private fun cancelPeriodicTask() {
        workManager.cancelWorkById(periodicWorkRequest.id)
    }
}