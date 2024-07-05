package com.huiiro.ncn.component.tab.more

import android.content.Intent
import android.net.Uri
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
                title = "快速链接：Huiiro Guide"
                image = ""
                action = "https://www.huiiro.com/tool/common/guide"
            }
        )

        adapter.submitList(moreItems)
    }

    override fun initListener() {
        super.initListener()
        adapter.onItemClick = { t ->
            //默认行为为打开浏览器，而不是打开页面
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(t.action))
            startActivity(intent)
        }
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
            Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
        }, 600)
    }
}