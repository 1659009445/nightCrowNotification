package com.huiiro.ncn

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * 全局Application配置
 */
class AppContext : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initMMKV()
    }

    /**
     * 初始化mmkv
     */
    private fun initMMKV() {
        val mmkv = MMKV.initialize(this)
    }

    /**
     * 全局context
     */
    companion object {
        lateinit var instance: AppContext
    }
}