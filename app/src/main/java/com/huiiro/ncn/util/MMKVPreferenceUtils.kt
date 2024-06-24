package com.huiiro.ncn.util

import com.tencent.mmkv.MMKV

object MMKVPreferenceUtils {

    /**
     * 获取用户是否通过导航页面
     */
    fun isShouGuide(): Boolean {
        return getBoolean(SHOW_GUIDE, true)
    }

    fun setShowGuide(value: Boolean) {
        putBoolean(SHOW_GUIDE, value)
    }

    /**
     * 获取用户是否关闭提醒
     */
    fun isUserCloseAlarm(): Boolean {
        return getBoolean(USER_CLOSE_ALARM, false)
    }

    fun setUserCloseAlarm(value: Boolean) {
        putBoolean(USER_CLOSE_ALARM, value)
    }

    /**
     * 获取用户关闭提醒key
     */
    fun getUserCloseAlarmKey(): String {
        return getString(USER_CLOSE_ALARM_KEY, "")
    }

    fun setUserCloseAlarmKey(value: String) {
        putString(USER_CLOSE_ALARM_KEY, value)
    }


    /**
     * 常量组
     */
    val p: MMKV by lazy {
        MMKV.defaultMMKV()!!
    }

    private const val SHOW_GUIDE = "show_guide"

    //该值为true时，不发送提醒给用户
    private const val USER_CLOSE_ALARM = "user_close_alarm"

    private const val USER_CLOSE_ALARM_KEY = "user_close_alarm_key"

    private fun getBoolean(key: String, value: Boolean): Boolean {
        return p.getBoolean(key, value)
    }

    private fun putBoolean(key: String, value: Boolean) {
        p.edit().putBoolean(key, value).apply()
    }

    private fun getString(key: String, value: String): String {
        return p.getString(key, value) ?: value
    }

    private fun putString(key: String, value: String) {
        p.edit().putString(key, value).apply()
    }
}