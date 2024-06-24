package com.huiiro.ncn.component.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.huiiro.ncn.R
import com.huiiro.ncn.base.activity.BaseViewModelActivity
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.component.login.LoginActivity
import com.huiiro.ncn.databinding.AppActivityBinding
import com.huiiro.ncn.databinding.ComponentItemTabBinding
import com.huiiro.ncn.core.ForegroundService

class AppActivity : BaseViewModelActivity<AppActivityBinding>() {

    companion object {
        private const val TAG = "AppActivity"

        private val indicatorTitles = intArrayOf(
            R.string.layout_index,
            R.string.layout_notice,
            R.string.layout_more,
            R.string.layout_my
        )

        private val indicatorIcons = intArrayOf(
            R.drawable.selector_tab_index,
            R.drawable.selector_tab_notice,
            R.drawable.selector_tab_more,
            R.drawable.selector_tab_my,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //开启后台服务
        Intent(this, ForegroundService::class.java).also { intent ->
            startForegroundService(intent)
            Log.d(TAG, "onCreate: 开启后台服务")
        }
    }

    override fun initDatum() {
        super.initDatum()

        //初始化滚动控件
        binding.apply {
            pager.offscreenPageLimit = indicatorTitles.size
            pager.adapter = AppAdapter(this@AppActivity, indicatorTitles.size)
        }

        //初始化底部导航栏控件
        for (i in indicatorTitles.indices) {
            ComponentItemTabBinding.inflate(layoutInflater).apply {
                content.setText(indicatorTitles[i])
                icon.setImageResource(indicatorIcons[i])
                binding.indicator.addView(root)
            }
        }

        //绑定滚动控件和导航栏控件
        ViewPager2Delegate.install(binding.pager, binding.indicator, false)

        //判断是否跳转登录页面
        val action = intent.action
        if (Constant.ACTION_LOGIN == action) {
            startActivity(LoginActivity::class.java)
        }
    }
}