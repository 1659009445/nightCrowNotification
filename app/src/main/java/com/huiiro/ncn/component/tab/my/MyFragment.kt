package com.huiiro.ncn.component.tab.my

import android.os.Bundle
import android.widget.Toast
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.core.helper.NotificationHelper
import com.huiiro.ncn.databinding.MyFragmentBinding

/**
 * 我的界面
 */
class MyFragment : BaseViewModelFragment<MyFragmentBinding>() {

    private lateinit var notificationHelper: NotificationHelper

    companion object {
        fun newInstance(): MyFragment {
            val args = Bundle()
            val fragment = MyFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initDatum() {
        super.initDatum()

        notificationHelper = NotificationHelper(requireContext())

        binding.manageNotification.setOnClickListener {
            if (notificationHelper.getNotificationStatus()) {
                Toast.makeText(context, "服务已在运行中...", Toast.LENGTH_SHORT).show()
            } else {
                notificationHelper.registerNotification()
                Toast.makeText(context, "服务正在启动中...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}