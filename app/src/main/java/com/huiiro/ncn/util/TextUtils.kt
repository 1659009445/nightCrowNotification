package com.huiiro.ncn.util

import android.text.method.LinkMovementMethod
import android.widget.TextView

/**
 * 文本处理工具
 */
object TextUtils {

    /**
     * 设置链接颜色
     */
    fun setLinkColor(view: TextView, color: Int) {
        view.movementMethod = LinkMovementMethod.getInstance()
        view.setLinkTextColor(color)
    }
}