package com.huiiro.ncn.core.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.huiiro.ncn.core.helper.UpdateHelper

class APKInstallerReceiver : BroadcastReceiver() {

    private lateinit var updateHelper: UpdateHelper

    companion object {
        const val TAG = "APKInstallerReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            Log.d(TAG, "onReceive: download finish, wait installing")
            Toast.makeText(context, "下载完成！", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceive: download finish, prepare installing")
            updateHelper.installApk()
        }
    }

}