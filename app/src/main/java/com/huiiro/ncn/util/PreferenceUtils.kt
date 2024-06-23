package com.huiiro.ncn.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferenceUtils(context: Context) {

    //获取上下文
    private var context: Context = context.applicationContext

    //获取系统偏好设置
    private var preference: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(this.context)

    //用户是否同意服务条款
    val isUserAgreePrivacyTerms: Boolean
        get() = preference.getBoolean(USER_AGREE_PRIVACY, false)

    /**
     * 用户同意隐私协议
     */
    fun setUserAgreePrivacyTerms() {
        setSystemPreference(USER_AGREE_PRIVACY, true)
    }

    private fun setSystemPreference(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    companion object {
        private const val NAME = "huiiro_night_crow_notification"
        private const val USER_AGREE_PRIVACY = "user_agree_privacy"

        @SuppressLint("StaticFieldLeak")
        private var instance: PreferenceUtils? = null

        @Synchronized
        fun getInstance(context: Context): PreferenceUtils {
            if (instance == null) {
                instance = PreferenceUtils(context)
            }
            return instance!!
        }
    }
}