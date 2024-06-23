package com.huiiro.ncn.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 基类事件
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * 初始化控件
     */
    protected open fun initViewData() {}

    /**
     * 初始化数据
     */
    protected open fun initDatum() {}

    /**
     * 初始化监听器
     */
    protected open fun initListener() {}


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initViewData()
        initDatum()
        initListener()
    }
}