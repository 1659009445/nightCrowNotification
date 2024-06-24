package com.huiiro.ncn.core

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.huiiro.ncn.component.alarm.AlarmDialogActivity

/**
 * 前台服务 |
 * 发起提醒功能
 */
class ForegroundAlarmService : Service() {

    companion object {
        const val TAG = "AlarmService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Foreground Service started")

        showAlertDialog()

        return START_STICKY
    }

    /**
     * 创建提醒对话框
     */
    private fun showAlertDialog() {
        val dialogIntent = Intent(this, AlarmDialogActivity::class.java)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }
}