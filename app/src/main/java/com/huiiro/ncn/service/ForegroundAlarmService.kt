package com.huiiro.ncn.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.huiiro.ncn.http.repository.CrowRepository
import com.huiiro.ncn.component.alarm.AlarmDialogActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = CrowRepository.crowWarning()
                val result = response.getData()?.result
                val value = response.getData()?.value
                Log.d(TAG, "onReceive: ${result}, ${value}")

                if (result == false) {
                    withContext(Dispatchers.Main) {
                        showAlertDialog()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in onStartCommand: ${e.message}")
            }
        }

        return START_STICKY
    }

    private fun showAlertDialog() {
        val dialogIntent = Intent(this, AlarmDialogActivity::class.java)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }
}