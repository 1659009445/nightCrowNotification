package com.huiiro.ncn.core.helper

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.edit
import java.io.File


class UpdateHelper(private val context: Context) {

    companion object {
        const val TAG = "UpdateHelper"
        const val FULL_CLAZZ_NAME = "com.huiiro.ncn.core.helper.UpdateHelper"
    }

    fun downloadAndInstallApk(apkUrl: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(apkUrl)
        val request = DownloadManager.Request(uri)
        //set request notification
        request.setTitle("NightCrowNotification Downloading Update")
        request.setDescription("Downloading update package")
        request.setMimeType("application/vnd.android.package-archive")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //set save oath
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk")

        Log.d(TAG, "downloadAndInstallApk: download task init finish...")
        val downloadId = downloadManager.enqueue(request)
        Log.d(TAG, "downloadAndInstallApk: try start download...")
        val sharedPreferences = context.getSharedPreferences(FULL_CLAZZ_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putLong("downloadId", downloadId)
            apply()
        }
    }

    fun installApk() {
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "update.apk"
            )
            val apkUri =
                FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val installIntent = Intent(Intent.ACTION_VIEW)
            installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            Log.d("installApk", "Starting install intent with URI: $apkUri")
            context.startActivity(installIntent)
            Log.d(TAG, "installApk: install success")
        } catch (e: Exception) {
            Log.e("installApk", "Error installing APK", e)
            Toast.makeText(context, "Error installing APK: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}