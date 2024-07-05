package com.huiiro.ncn.component.tab.notice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.NoticeFragmentBinding
import kotlinx.coroutines.launch

/**
 * 通知界面
 */
class NoticeFragment : BaseViewModelFragment<NoticeFragmentBinding>() {

    private lateinit var viewModel: NoticeViewModel
    private lateinit var adapter: NoticeAdapter

    companion object {
        fun newInstance(): NoticeFragment {
            val args = Bundle()
            val fragment = NoticeFragment()
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
        viewModel = ViewModelProvider(this)[NoticeViewModel::class.java]

        adapter = NoticeAdapter()
        binding.list.adapter = adapter

        lifecycleScope.launch {
            viewModel.data.collect {
                adapter.submitList(it.getData())
            }
        }
        viewModel.loadData()
    }

    override fun initListener() {
        super.initListener()
        adapter.onItemClick = { t ->
            val intent = Intent(activity, NoticeDetailActivity::class.java)
            intent.putExtra(Constant.NOTICE_ID, t.aid)
            startActivity(intent)
        }
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.loadData()
            Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
        }, 600)
    }
}