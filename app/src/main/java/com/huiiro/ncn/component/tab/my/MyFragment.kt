package com.huiiro.ncn.component.tab.my

import android.os.Bundle
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.MyFragmentBinding

/**
 * 我的界面
 */
class MyFragment : BaseViewModelFragment<MyFragmentBinding>() {

    companion object {
        fun newInstance(): MyFragment {
            val args = Bundle()
            val fragment = MyFragment()
            fragment.arguments = args
            return fragment
        }
    }
}