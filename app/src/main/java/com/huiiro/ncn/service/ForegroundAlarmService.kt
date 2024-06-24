package com.huiiro.ncn.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.huiiro.ncn.component.alarm.AlarmDialogActivity

/**
 * 前台服务播放提醒音乐
 */
class ForegroundAlarmService : Service() {

    companion object {
        const val TAG = "AlarmService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started")

        showAlertDialog()

        return START_STICKY
    }

    private fun showAlertDialog() {
        val dialogIntent = Intent(this, AlarmDialogActivity::class.java)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }
}