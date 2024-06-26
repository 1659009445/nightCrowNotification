package com.huiiro.ncn.core.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.huiiro.ncn.AppContext
import com.huiiro.ncn.R
import com.huiiro.ncn.http.repository.CrowRepository
import com.huiiro.ncn.core.ForegroundAlarmService
import com.huiiro.ncn.util.MMKVPreferenceUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 接收 用户 Action 行为 通知 |
 * 该方法会判断并执行通知提醒功能
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

        //发送请求并判断
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = CrowRepository.crowWarning()

                val result = response.getData()?.result
                val key = response.getData()?.key

                Log.d(TAG, "onReceive: ${result}, ${response.getData()?.value}, $key")

                if (result == true) {
                    withContext(Dispatchers.Main) {
                        check(key, context)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in onStartCommand: ${e.message}")
            }
        }
    }

    /**
     * 判断是否执行提醒
     */
    private fun check(key: String?, context: Context?) {
        //判断key是否相同 key相同代表是同一次提醒事件
        if (!key.equals(MMKVPreferenceUtils.getUserCloseAlarmKey())) {
            //key不相等 设置 user_close_alarm false 并更新key值
            MMKVPreferenceUtils.setUserCloseAlarm(false)
            MMKVPreferenceUtils.setUserCloseAlarmKey(key!!)
            Log.d(TAG, "onReceive: key is not same")
        }

        //如果该值为 true 代表用户关闭提醒
        if (MMKVPreferenceUtils.isUserCloseAlarm()) {
            Log.d(TAG, "onReceive: user close alarm")
        } else {
            //执行提醒逻辑
            if (AppContext.isAppInForeground) {
                //前台提醒方式
                Log.d(TAG, "onReceive: frontend alarm")
                context?.let {
                    val serviceIntent = Intent(it, ForegroundAlarmService::class.java)
                    it.startService(serviceIntent)
                }
            } else {
                //后台提醒方式
                Log.d(TAG, "onReceive: background alarm")
                playAlarmSound(context)
            }
        }
    }

    /**
     * 播放音乐
     */
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

    /**
     * 停止播放音乐
     */
    fun stopAlarmSound() {
        Log.d(TAG, "stopAlarmSound: ")
        mediaPlayer?.apply {
            stop()
            release()
            mediaPlayer = null
        }
    }

    /**
     * 本次永久停止播放音乐
     */
    fun stopAlarmSoundForever() {
        MMKVPreferenceUtils.setUserCloseAlarm(true)
        stopAlarmSound()
    }
}
