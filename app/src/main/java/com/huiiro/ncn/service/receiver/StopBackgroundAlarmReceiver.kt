package com.huiiro.ncn.service.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * 接受停止后台播放音乐广播
 */
class StopBackgroundAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmService"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Stop alarm button clicked")
        context?.let {

            //停止播放音乐
            AlarmReceiver().stopAlarmSound()

            // 停止前台服务
            //it.stopService(Intent(it, ForegroundService::class.java))
        }
    }
}
