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
        val action = intent?.getStringExtra("action")
        Log.d(TAG, "onReceive: $action")
        context?.let {
            when (action) {
                "stop_once" -> {
                    // 停止播放音乐一次
                    AlarmReceiver().stopAlarmSound()
                }

                "stop_forever" -> {
                    // 永久停止播放音乐
                    AlarmReceiver().stopAlarmSoundForever()
                }
            }
            // 停止前台服务
            //it.stopService(Intent(it, ForegroundService::class.java))
        }
    }
}
