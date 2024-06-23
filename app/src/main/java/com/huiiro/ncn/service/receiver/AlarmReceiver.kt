package com.huiiro.ncn.service.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.huiiro.ncn.AppContext
import com.huiiro.ncn.R
import com.huiiro.ncn.service.ForegroundAlarmService

/**
 * 接受定时任务广播
 */
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmReceiver"
        private var mediaPlayer: MediaPlayer? = null

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: AlarmReceiver? = null

        fun getInstance(): AlarmReceiver {
            return instance ?: synchronized(this) {
                instance ?: AlarmReceiver().also { instance = it }
            }
        }
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Alarm received, starting service")

        if (AppContext.isAppInForeground) {
            //foreground
            context?.let {
                val serviceIntent = Intent(it, ForegroundAlarmService::class.java)
                it.startService(serviceIntent)
            }
        } else {
            //background
            playAlarmSound(context)
        }
    }

    private fun playAlarmSound(context: Context?) {
        Log.d(TAG, "playAlarmSound: ")
        context?.let {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(it, R.raw.alarm_background)
                mediaPlayer?.apply {
                    isLooping = true
                    start()
                }
            } else {
                mediaPlayer?.apply {
                    if (!isPlaying) {
                        start()
                    }
                }
            }
        }
    }

    fun stopAlarmSound() {
        Log.d(TAG, "stopAlarmSound: ")
        mediaPlayer?.apply {
            stop()
            release()
            mediaPlayer = null
        }
    }
}
