package com.huiiro.ncn.component.alarm

import android.media.MediaPlayer
import com.huiiro.ncn.R
import com.huiiro.ncn.base.activity.BaseViewModelActivity
import com.huiiro.ncn.databinding.ComponentAlarmActivityBinding
import com.huiiro.ncn.util.MMKVPreferenceUtils

class AlarmDialogActivity : BaseViewModelActivity<ComponentAlarmActivityBinding>() {

    private lateinit var mediaPlayer: MediaPlayer


    override fun initViewData() {
        super.initViewData()

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_foreground)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        //停止一次
        binding.stopAlarmButtonOnce.setOnClickListener {
            stopAlarm()
        }

        //本次停止
        binding.stopAlarmButtonForever.setOnClickListener {
            //用户关闭提醒
            MMKVPreferenceUtils.setUserCloseAlarm(true)
            stopAlarm()
        }
    }

    private fun stopAlarm() {
        mediaPlayer.stop()
        mediaPlayer.release()
        finish()
    }
}