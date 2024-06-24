package com.huiiro.ncn.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.huiiro.ncn.core.helper.NotificationHelper

/**
 * 接收 用户手动关闭 Notification 通知 |
 * 该方法会重新创建 Notification ,防止用户手动关闭
 */
class NotificationDismissedReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "NotificationDismissedReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getIntExtra("notification_id", -1)
        Log.d(TAG, "onReceive: notificationId: $notificationId")

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.registerNotification()
    }
}