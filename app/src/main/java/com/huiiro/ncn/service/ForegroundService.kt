package com.huiiro.ncn.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.huiiro.ncn.R
import com.huiiro.ncn.service.receiver.AlarmReceiver
import com.huiiro.ncn.service.receiver.StopBackgroundAlarmReceiver
import com.huiiro.ncn.service.worker.CheckCrowWorker
import java.util.concurrent.TimeUnit

/**
 * 前台服务
 */
class ForegroundService : Service() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var alarmIntent: PendingIntent


    companion object {
        const val TAG = "ForegroundService"
        const val CHANNEL_ID = "huiiro_ncn_channel"
        const val CHANNEL_NAME = "huiiro_ncn_channel_name"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        start()
        startSchedule()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun start() {
        val stopIntent = Intent(this, StopBackgroundAlarmReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "huiiro_ncn_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("GB NightCrow Assistant")
            .setContentText("The Application is running...")
            .addAction(R.drawable.ic_launcher_foreground, "Stop Alarm", stopPendingIntent)
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

    private fun startSchedule() {
        //添加定时任务控件
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        //设置alarmIntent
        alarmIntent = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //设置提醒时间
        //val intervalMillis = 5 * 60 * 1000L
        //val startTime = TimeUtils.getNext5MultipleTimeMillis()
        val intervalMillis = 1 * 60 * 1000L
        val startTime = System.currentTimeMillis()
        Log.d(TAG, "startSchedule: ${startTime}")

        //设置提醒事件
        //alarmManager.set(AlarmManager.RTC_WAKEUP, 1000, alarmIntent)
        alarmManager.setRepeating(
            AlarmManager.RTC,
            startTime,
            intervalMillis,
            alarmIntent
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
}