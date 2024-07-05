package com.huiiro.ncn.component.tab.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.R
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.component.splash.SplashUpdateDialogFragment
import com.huiiro.ncn.core.helper.NotificationHelper
import com.huiiro.ncn.databinding.SettingsFragmentBinding
import com.huiiro.ncn.domain.CrowUpdateEntity
import com.huiiro.ncn.http.repository.CrowRepository
import kotlinx.coroutines.launch

/**
 * 我的界面
 */
class SettingsFragment : BaseViewModelFragment<SettingsFragmentBinding>() {

    private lateinit var notificationHelper: NotificationHelper
    private lateinit var updateEntity: CrowUpdateEntity

    companion object {
        const val TAG = "MyFragment"
        fun newInstance(): SettingsFragment {
            val args = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initDatum() {
        super.initDatum()

        val content = Html.fromHtml(getString(R.string.thank_project_url))
        val update = Html.fromHtml(getString(R.string.update_project_url))
        binding.thankProjectUrl.text = content
        binding.updateProjectUrl.text = update

        notificationHelper = NotificationHelper(requireContext())

        //检查版本更新字段
        lifecycleScope.launch {
            val crowUpdateEntity = CrowRepository.checkUpdate().getData()!!
            updateEntity = crowUpdateEntity
            if (updateEntity.code == Constant.VERSION_CODE) {
                binding.currentVersion.text = "当前为最新版本：${Constant.VERSION_NAME}"
            } else {
                binding.currentVersion.text = "当前版本：${Constant.VERSION_NAME}， 有新版本可供升级。"
            }
        }

        //开启服务通知
        binding.manageNotification.setOnClickListener {
            if (notificationHelper.getNotificationStatus()) {
                Toast.makeText(context, "服务已在运行中...", Toast.LENGTH_SHORT).show()
            } else {
                notificationHelper.registerNotification()
                Toast.makeText(context, "服务正在启动中...", Toast.LENGTH_SHORT).show()
            }
        }

        //清理缓存
        binding.clearCache.setOnClickListener {
            Toast.makeText(context, "缓存清理成功!", Toast.LENGTH_SHORT).show()
        }

        //检查更新
        binding.manageUpdate.setOnClickListener {
            if (updateEntity.code == Constant.VERSION_CODE) {
                Toast.makeText(context, "当前已是最新版本！", Toast.LENGTH_SHORT).show()
            } else {
                SplashUpdateDialogFragment.show(
                    requireFragmentManager()
                ) {}
            }
        }

        //设置自定义铃声
        binding.setAlarm.setOnClickListener {
            Toast.makeText(context, "该功能即将上线，敬请期待！", Toast.LENGTH_SHORT).show()
        }

        //获取运行日志
        binding.readLog.setOnClickListener {
            Toast.makeText(context, "该功能即将上线，敬请期待！", Toast.LENGTH_SHORT).show()
        }
    }
}