package com.huiiro.ncn.base.activity

import android.content.Intent

open class BaseCommonActivity : BaseActivity() {

    fun startActivity(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    fun startActivityAfterFinishThisActivity(clazz: Class<*>) {
        startActivity(clazz)
        finish()
    }
}