package com.huiiro.ncn.component.splash

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.base.activity.BaseViewModelActivity
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.component.app.AppActivity
import com.huiiro.ncn.component.guide.GuideActivity
import com.huiiro.ncn.component.splash.SplashUpdateDialogFragment.Companion.TAG
import com.huiiro.ncn.databinding.SplashActivityBinding
import com.huiiro.ncn.http.repository.CrowRepository
import com.huiiro.ncn.util.MMKVPreferenceUtils
import com.huiiro.ncn.util.PreferenceUtils
import com.huiiro.ncn.util.ThemeUtils
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseViewModelActivity<SplashActivityBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //using this to load custom splash screen
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun initViewData() {
        //设置UI样式
        QMUIStatusBarHelper.translucent(this)

        //设置UI主题
        if (ThemeUtils.isDark(this))
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        else
            QMUIStatusBarHelper.setStatusBarLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initDatum() {
        //设置服务条款对话框
        if (PreferenceUtils.getInstance(this).isUserAgreePrivacyTerms) {
            //获取应用必须权限
            requestPermission()
        } else {
            //获取用户协议对话框
            showUserAgreeTermsServiceDialog()
        }
    }

    /**
     * 展示用户协议对话框
     */
    private fun showUserAgreeTermsServiceDialog() {
        SplashTermsServiceDialogFragment.show(
            supportFragmentManager
        ) {
            //同意服务协议回调
            PreferenceUtils.getInstance(this).setUserAgreePrivacyTerms()
            //第一次同意后请求权限
            requestPermission()
        }
    }

    /**
     * 请求应用动态权限
     */
    private fun requestPermission() {
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(android.Manifest.permission.POST_NOTIFICATIONS)
            permissions.add(android.Manifest.permission.USE_EXACT_ALARM)
            //request for this permission will cause crash
            //permissions.add(android.Manifest.permission.SCHEDULE_EXACT_ALARM)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissions.add(android.Manifest.permission.FOREGROUND_SERVICE_REMOTE_MESSAGING)
        }

        PermissionX
            .init(this)
            .permissions(permissions)
            .request { allGranted, _, _ ->
                if (allGranted) {
                    checkUpdate()
                } else {
                    finish()
                }
            }
    }

    /**
     * 检查更新
     */
    private fun checkUpdate() {
        lifecycleScope.launch {
            val crowUpdateEntity = CrowRepository.checkUpdate().getData()!!
            Log.d(
                TAG, "checkUpdate: current version: ${Constant.VERSION_CODE}, " +
                        "newest version: ${crowUpdateEntity.code} "
            )
            if (Constant.VERSION_CODE < crowUpdateEntity.code!!) {
                Log.d(TAG, "initListener: detected newest version to download")
                SplashUpdateDialogFragment.show(
                    supportFragmentManager
                ) {
                    router2Index()
                }
            } else {
                router2Index()
            }
        }
    }

    /**
     * 跳转至首页
     */
    private fun router2Index() {
        //跳转到引导页
        if (MMKVPreferenceUtils.isShouGuide()) {
            startActivityAfterFinishThisActivity(GuideActivity::class.java)
            return
        }
        //跳转到主界面
        startActivityAfterFinishThisActivity(AppActivity::class.java)
    }
}