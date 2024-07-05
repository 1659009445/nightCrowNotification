package com.huiiro.ncn.core.helper

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.huiiro.ncn.R
import com.huiiro.ncn.base.consts.NotificationConstants
import com.huiiro.ncn.component.app.AppActivity
import com.huiiro.ncn.core.receiver.NotificationDismissedReceiver
import com.huiiro.ncn.core.receiver.StopBackgroundAlarmReceiver

/**
 * 创建 Notification
 */
class NotificationHelper(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private var notificationStatus: Boolean = false

    init {
        registerNotification()
    }

    fun getNotificationStatus(): Boolean {
        return notificationStatus
    }

    /**
     * 创建 Notification
     */
    fun createNotification(): Notification {

        //最小化唤醒事件
        val mainActivityIntent = Intent(context, AppActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainActivityPendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        //注销事件
        val dismissIntent = Intent(context, NotificationDismissedReceiver::class.java).apply {
            putExtra(
                NotificationConstants.NOTIFICATION_DISMISS_PARAM,
                NotificationConstants.NOTIFICATION_DISMISS_VALUE
            )
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            -1,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        //停止一次事件
        val stopOnceIntent = Intent(context, StopBackgroundAlarmReceiver::class.java).apply {
            putExtra(
                NotificationConstants.NOTIFICATION_ACTION_PARAM,
                NotificationConstants.NOTIFICATION_ACTION_STOP_ONCE
            )
        }
        val stopOncePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            stopOnceIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        //关闭本次通知事件
        val stopForeverIntent = Intent(context, StopBackgroundAlarmReceiver::class.java).apply {
            putExtra(
                NotificationConstants.NOTIFICATION_ACTION_PARAM,
                NotificationConstants.NOTIFICATION_ACTION_STOP_FOREVER
            )
        }
        val stopForeverPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            stopForeverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        //关闭Notification
        val stopNotificationIntent =
            Intent(context, StopBackgroundAlarmReceiver::class.java).apply {
                putExtra(
                    NotificationConstants.NOTIFICATION_ACTION_PARAM,
                    NotificationConstants.NOTIFICATION_ACTION_STOP_NOTIFICATION
                )
            }
        val stopNotificationPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            stopNotificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        notificationStatus = true

        return NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setOngoing(true)
            .setSound(null)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(R.drawable.res_pic_coin)
            .setContentTitle(NotificationConstants.CONTENT_TITLE)
            .setContentText(NotificationConstants.CONTENT_TEXT)
            .setDeleteIntent(dismissPendingIntent)
            .setContentIntent(mainActivityPendingIntent)
            .addAction(R.drawable.res_pic_coin, "Stop Once", stopOncePendingIntent)
            .addAction(R.drawable.res_pic_coin, "Stop Forever", stopForeverPendingIntent)
            .addAction(
                R.drawable.res_pic_coin,
                "Stop Notification",
                stopNotificationPendingIntent
            )
            .build()
    }

    /**
     * 注册 Notification
     */
    fun registerNotification() {
        val notification = createNotification()
        notificationManager.notify(1, notification)
    }

    fun destroyNotificationCallback() {
        notificationStatus = false
    }
}