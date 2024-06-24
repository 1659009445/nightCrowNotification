package com.huiiro.ncn

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.huiiro.ncn.util.MMKVPreferenceUtils
import com.tencent.mmkv.MMKV

/**
 * 全局Application配置
 */
class AppContext : Application(), Application.ActivityLifecycleCallbacks {

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    companion object {
        private const val TAG = "AppContext"
        lateinit var instance: AppContext
        var isAppInForeground = false
            private set
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        instance = this
        initMMKV()
        //每次启动重置该提醒功能
        MMKVPreferenceUtils.setUserCloseAlarm(false)
        Log.d(TAG, "onCreate: set user close alarm: ${MMKVPreferenceUtils.isUserCloseAlarm()}")
    }

    /**
     * 初始化mmkv
     */
    private fun initMMKV() {
        MMKV.initialize(this)
    }

    /**
     * 生命钩子
     */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    /**
     * App enters foreground
     */
    override fun onActivityStarted(activity: Activity) {
        if (activityReferences == 0 && !isActivityChangingConfigurations) {
            onAppForegrounded()
            isAppInForeground = true
        }
        activityReferences++
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    /**
     * App enters background
     */
    override fun onActivityStopped(activity: Activity) {
        activityReferences--
        isActivityChangingConfigurations = activity.isChangingConfigurations
        if (activityReferences == 0 && !isActivityChangingConfigurations) {
            onAppBackgrounded()
            isAppInForeground = false
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    private fun onAppForegrounded() {
        Log.d("Application", "App is in foreground")
    }

    private fun onAppBackgrounded() {
        Log.d("Application", "App is in background")
    }
}