package com.huiiro.ncn.component.tab.more

import android.os.Bundle
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.MoreFragmentBinding

/**
 * 更多界面
 */
class MoreFragment : BaseViewModelFragment<MoreFragmentBinding>() {

    companion object {
        fun newInstance(): MoreFragment {
            val args = Bundle()
            val fragment = MoreFragment()
            fragment.arguments = args
            return fragment
        }
    }
}