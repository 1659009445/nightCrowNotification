package com.huiiro.ncn.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.huiiro.ncn.R
import com.huiiro.ncn.service.worker.CheckCrowWorker
import java.util.concurrent.TimeUnit

class ForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return START_STICKY
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "huiiro_ncn_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("GB NightCrow Assistant")
            .setContentText("The Application is running...")
            .build()

        startForeground(1, notification)
    }

    private fun startWork() {
        //creating work task using WorkManager will exec every 15minutes
        val constraints = Constraints.Builder().build()
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<CheckCrowWorker>(5, TimeUnit.MINUTES).setConstraints(
                constraints
            ).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "CheckCrowWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    companion object {
        const val CHANNEL_ID = "huiiro_ncn_channel"
        const val CHANNEL_NAME = "huiiro_ncn_channel_name"
    }
}