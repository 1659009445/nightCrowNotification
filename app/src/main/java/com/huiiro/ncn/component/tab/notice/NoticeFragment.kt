package com.huiiro.ncn.component.tab.notice

import android.os.Bundle
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.NoticeFragmentBinding

/**
 * 通知界面
 */
class NoticeFragment : BaseViewModelFragment<NoticeFragmentBinding>() {

    companion object {
        fun newInstance(): NoticeFragment {
            val args = Bundle()
            val fragment = NoticeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}