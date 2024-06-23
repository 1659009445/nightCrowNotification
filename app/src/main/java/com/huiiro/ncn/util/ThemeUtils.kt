package com.huiiro.ncn.util

import android.content.Context
import android.content.res.Configuration

/**
 * 主题工具类
 */
object ThemeUtils {

    /**
     * 判断是否是Dark Mode
     */
    fun isDark(context: Context): Boolean {
        return context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}