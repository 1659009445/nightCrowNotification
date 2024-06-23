package com.huiiro.ncn.component.guide

import android.os.Bundle
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.GuideFragmentViewpagerBinding

/**
 * 引导界面
 */
class GuideFragment : BaseViewModelFragment<GuideFragmentViewpagerBinding>() {

    override fun initDatum() {
        super.initDatum()
        val data = requireArguments().getInt(Constant.GUIDE_LOGO)
        binding.guideLogo.setImageResource(data)
    }

    companion object {
        fun newInstance(data: Int): GuideFragment {
            val args = Bundle()
            args.putInt(Constant.GUIDE_LOGO, data)

            val fragment = GuideFragment()
            fragment.arguments = args
            return fragment
        }
    }
}