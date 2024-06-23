package com.huiiro.ncn.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * 屏幕工具类
 */
object ScreenUtils {

    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outDisplayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outDisplayMetrics)
        return outDisplayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outDisplayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outDisplayMetrics)
        return outDisplayMetrics.heightPixels
    }
}