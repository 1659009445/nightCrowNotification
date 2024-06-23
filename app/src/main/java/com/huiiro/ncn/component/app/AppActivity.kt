package com.huiiro.ncn.component.app

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
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
import com.huiiro.ncn.service.ForegroundService
import com.huiiro.ncn.service.receiver.AlarmReceiver
import com.huiiro.ncn.util.TimeUtils

class AppActivity : BaseViewModelActivity<AppActivityBinding>() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var alarmIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //开启后台服务
        Intent(this, ForegroundService::class.java).also { intent ->
            startForegroundService(intent)
        }

        //添加定时任务控件
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        alarmIntent = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val intervalMillis = 1 * 60 * 1000L
        val startTime = TimeUtils.getNext5MultipleTimeMillis()
        Log.d("AppActivity", "onCreate: ${startTime}")
        //alarmManager.set(AlarmManager.RTC_WAKEUP, 1000, alarmIntent)
        alarmManager.setRepeating(
            AlarmManager.RTC,
            startTime,
            intervalMillis,
            alarmIntent
        )
    }

    override fun initViewData() {
        super.initViewData()
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

    companion object {
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
}