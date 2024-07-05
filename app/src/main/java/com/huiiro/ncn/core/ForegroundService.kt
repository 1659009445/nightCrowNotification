package com.huiiro.ncn.core

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.huiiro.ncn.base.consts.NotificationConstants
import com.huiiro.ncn.core.helper.NotificationHelper
import com.huiiro.ncn.core.receiver.AlarmReceiver
import com.huiiro.ncn.core.worker.CheckCrowWorker
import com.huiiro.ncn.util.FormatUtils
import java.util.concurrent.TimeUnit

/**
 * 前台服务
 */
class ForegroundService : Service() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var alarmIntent: PendingIntent


    companion object {
        const val TAG = "ForegroundService"
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
        Log.d(TAG, "onStartCommand: onStartCommand")
        return START_STICKY
    }

    /**
     * 创建 Foreground 前台服务
     */
    private fun start() {
        Log.d(TAG, "start: enter notification start method")

        val notificationHelper = NotificationHelper(this)
        val notification = notificationHelper.createNotification()

        startForeground(1, notification)
    }

    /**
     * 创建 Work 定时任务
     */
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

    /**
     * 创建 Alarm 定时任务
     */
    private fun startSchedule() {
        //添加定时任务控件
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        //设置alarmIntent
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        //设置提醒时间
        val intervalMillis = 5 * 60 * 1000L
        val startTime = FormatUtils.getNext5MultipleTimeMillis()

        //val intervalMillis = 1 * 60 * 1000L
        //val startTime = System.currentTimeMillis()

        Log.d(TAG, "startSchedule: $startTime")

        //设置提醒事件
        //alarmManager.set(AlarmManager.RTC_WAKEUP, 1000, alarmIntent)
        alarmManager.setRepeating(
            AlarmManager.RTC,
            startTime,
            intervalMillis,
            alarmIntent
        )
    }

    /**
     * 创建 Channel
     */
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            NotificationConstants.CHANNEL_ID,
            NotificationConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }
}