package com.huiiro.ncn.util

import android.icu.text.NumberFormat

object FormatUtils {

    /**
     * eg 2024-06-22 12:00:00
     * return 2024-06-22
     */
    fun getDate(time: String): String {
        return time.split(" ")[0]
    }

    /**
     * eg 2024-06-22 12:00:00
     * return 12:00:00
     */
    fun getTime(time: String): String {
        return time.split(" ")[1]
    }

    /**
     * eg 2024-06-22 12:00:00
     * return 06-22 12:00
     */
    fun getSimpleTime(time: String): String {
        val datePart = time.split(" ")[0].substring(5)
        val timePart = time.split(" ")[1].substring(0, 5)
        return "$datePart $timePart"
    }

    /**
     * eg 2024-06-22 12:00:00
     * return 06-22 12:00:00
     */
    fun getSimpleTimeWithSecond(time: String): String {
        val datePart = time.split(" ")[0].substring(5)
        val timePart = time.split(" ")[1]
        return "$datePart $timePart"
    }

    /**
     * 计算 5 的倍数的时间戳
     */
    fun getNext5MultipleTimeMillis(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val currentMinute = (currentTimeMillis / 60000) % 60

        val minutesUntilNextMultipleOfFive = if (currentMinute % 5 == 0L) {
            0
        } else {
            5 - (currentMinute % 5)
        }
        return currentTimeMillis + minutesUntilNextMultipleOfFive * 60000
    }

    fun formatDouble(it: Double, i: Int): String {
        val instance = NumberFormat.getInstance()
        instance.isGroupingUsed = false
        instance.maximumFractionDigits= i
        val result = instance.format(it)
        return result
    }

}
