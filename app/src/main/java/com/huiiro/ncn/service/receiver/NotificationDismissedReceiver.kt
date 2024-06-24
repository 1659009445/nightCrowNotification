package com.huiiro.ncn.service.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.huiiro.ncn.R

class NotificationDismissedReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "NotificationDismissedReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getIntExtra("notification_id", -1)
        Log.d(TAG, "onReceive: notificationId: $notificationId")
        val newNotification = createNotification(context)
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, newNotification)
    }

    private fun createNotification(context: Context?): Notification {
        val dismissIntent = Intent(context, NotificationDismissedReceiver::class.java).apply {
            putExtra("notification_id", -1)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            -1,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopOnceIntent = Intent(context, StopBackgroundAlarmReceiver::class.java).apply {
            putExtra("action", "stop_once")
        }
        val stopOncePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            stopOnceIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopForeverIntent = Intent(context, StopBackgroundAlarmReceiver::class.java).apply {
            putExtra("action", "stop_forever")
        }
        val stopForeverPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            stopForeverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context!!, "huiiro_ncn_channel")
            .setOngoing(true)
            .setSound(null)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("GB NightCrow Assistant")
            .setContentText("The Application is running again...")
            .setDeleteIntent(dismissPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Stop Once", stopOncePendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Stop Forever", stopForeverPendingIntent)
            .build()
    }
}