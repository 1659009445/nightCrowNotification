package com.huiiro.ncn.util

import com.tencent.mmkv.MMKV

object MMKVPreferenceUtils {

    val p: MMKV by lazy {
        MMKV.defaultMMKV()!!
    }

    fun isShouGuide(): Boolean {
        return getBoolean(SHOW_GUIDE, true)
    }

    fun setShowGuide(value: Boolean) {
        putBoolean(SHOW_GUIDE, value)
    }

    private const val SHOW_GUIDE = "show_guide"

    private fun getBoolean(key: String, value: Boolean): Boolean {
        return p.getBoolean(key, value)
    }

    private fun putBoolean(key: String, value: Boolean) {
        p.edit().putBoolean(key, value).apply()
    }
}