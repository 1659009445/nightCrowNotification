package com.huiiro.ncn.component.tab.index

import android.os.Bundle
import com.huiiro.ncn.base.adapter.TabLayoutViewPager2Mediator
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.IndexFragmentBinding
import com.huiiro.ncn.domain.common.Category

/**
 * 首页界面
 */
class IndexFragment : BaseViewModelFragment<IndexFragmentBinding>() {

    companion object {
        private val indexIndicatorCategories: List<Category> = listOf(
            Category.create("1", "  铸造鸦币  "),
            Category.create("2", "  游戏货币  "),
            Category.create("3", "  数字货币  "),
        )

        fun getIndexIndicatorCategories(): List<Category> {
            return indexIndicatorCategories
        }

        fun newInstance(): IndexFragment {
            val args = Bundle()

            val fragment = IndexFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initDatum() {
        super.initDatum()
        binding.apply {
            pager.adapter = IndexAdapter(requireActivity(), indexIndicatorCategories)
            TabLayoutViewPager2Mediator(indicator, pager) { _, _ -> }.attach()
        }
    }
}