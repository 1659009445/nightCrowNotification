package com.huiiro.ncn.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.huiiro.ncn.base.consts.NotificationConstants
import com.huiiro.ncn.core.ForegroundService

/**
 * 接收 用户 Action 行为 通知 |
 * 该方法会处理后台服务 Notification
 */
class StopBackgroundAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmService"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getStringExtra("action")
        Log.d(TAG, "onReceive: action: $action")

        context?.let {
            when (action) {
                // 停止播放音乐一次
                NotificationConstants.NOTIFICATION_ACTION_STOP_ONCE -> {
                    AlarmReceiver().stopAlarmSound()
                }

                // 永久停止播放音乐
                NotificationConstants.NOTIFICATION_ACTION_STOP_FOREVER -> {
                    AlarmReceiver().stopAlarmSoundForever()
                }

                // 停止前台服务
                NotificationConstants.NOTIFICATION_ACTION_STOP_NOTIFICATION -> {
                    it.stopService(Intent(it, ForegroundService::class.java))
                }

                else -> {}
            }
        }
    }
}
