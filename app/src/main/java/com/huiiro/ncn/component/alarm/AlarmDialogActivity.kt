package com.huiiro.ncn.component.alarm

import android.media.MediaPlayer
import com.huiiro.ncn.R
import com.huiiro.ncn.base.activity.BaseViewModelActivity
import com.huiiro.ncn.databinding.ComponentAlarmActivityBinding

class AlarmDialogActivity : BaseViewModelActivity<ComponentAlarmActivityBinding>() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun initViewData() {
        super.initViewData()

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_foreground)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        binding.stopAlarmButton.setOnClickListener {
            stopAlarm()
        }
    }

    private fun stopAlarm() {
        mediaPlayer.stop()
        mediaPlayer.release()
        finish()
    }
}