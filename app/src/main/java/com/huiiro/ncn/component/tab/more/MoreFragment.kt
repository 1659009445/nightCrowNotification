package com.huiiro.ncn.component.tab.more

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.MoreFragmentBinding
import com.huiiro.ncn.domain.common.MoreEntity

/**
 * 更多界面
 */
class MoreFragment : BaseViewModelFragment<MoreFragmentBinding>() {

    private lateinit var adapter: MoreAdapter

    companion object {
        fun newInstance(): MoreFragment {
            val args = Bundle()
            val fragment = MoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViewData() {
        super.initViewData()
        binding.swipeRefreshLayout.setOnRefreshListener { refreshData() }
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initDatum() {
        super.initDatum()
        adapter = MoreAdapter()
        binding.list.adapter = adapter

        val moreItems = listOf(
            MoreEntity().apply {
                title = "即将更新，敬请期待"
                image = "image_url"
                action = "action"
            }
        )

        adapter.submitList(moreItems)
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
            Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
        }, 600)
    }
}