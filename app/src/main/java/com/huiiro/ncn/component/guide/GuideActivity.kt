package com.huiiro.ncn.component.guide

import android.content.Intent
import android.util.Log
import com.huiiro.ncn.R
import com.huiiro.ncn.base.activity.BaseViewModelActivity
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.component.app.AppActivity
import com.huiiro.ncn.databinding.GuideActivityBinding
import com.huiiro.ncn.util.MMKVPreferenceUtils

class GuideActivity : BaseViewModelActivity<GuideActivityBinding>() {

    private lateinit var adapter: GuideAdapter

    companion object {
        private const val TAG = "GuideActivity"
    }

    override fun initDatum() {
        super.initDatum()

        //创建adapter控件
        adapter = GuideAdapter(this)
        //绑定适配器到控件
        binding.guideViewpage.adapter = adapter
        //绑定指示器
        binding.guideIndicator.setViewPager(binding.guideViewpage)
        adapter.registerAdapterDataObserver(binding.guideIndicator.adapterDataObserver)
        //为适配器设置数据
        val datum: MutableList<Int> = ArrayList()
        datum.add(R.drawable.res_pic_guide1)
        datum.add(R.drawable.res_pic_guide2)
        datum.add(R.drawable.res_pic_guide3)
        //绑定数据至适配器
        adapter.setDatum(datum)
    }

    override fun initListener() {
        super.initListener()
        //登录或注册按钮
        binding.guideLoginBtn.setOnClickListener {
            Log.d(TAG, "initListener: user click login btn ")
            setShowGuide()
            val intent = Intent(this, AppActivity::class.java)
            intent.action = Constant.ACTION_LOGIN
            startActivity(intent)
            finish()
        }

        //开启体验按钮
        binding.guideExperienceBtn.setOnClickListener {
            Log.d(TAG, "initListener: user click experience btn ")
            setShowGuide()
            startActivityAfterFinishThisActivity(AppActivity::class.java)
        }
    }

    private fun setShowGuide() {
        MMKVPreferenceUtils.setShowGuide(false)
    }
}