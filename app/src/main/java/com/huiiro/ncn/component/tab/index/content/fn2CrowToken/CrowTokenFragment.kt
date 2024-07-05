package com.huiiro.ncn.component.tab.index.content.fn2CrowToken

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.IndexContentCrowTokenBinding
import kotlinx.coroutines.launch

class CrowTokenFragment : BaseViewModelFragment<IndexContentCrowTokenBinding>() {

    private lateinit var viewModel: CrowTokenViewModel
    private lateinit var adapter: CrowTokenAdapter

    companion object {
        fun newInstance(categoryId: String? = null): CrowTokenFragment {
            val args = Bundle()

            categoryId?.let { args.putString(Constant.ID, categoryId) }

            val fragment = CrowTokenFragment()
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
        viewModel = ViewModelProvider(this)[CrowTokenViewModel::class.java]

        adapter = CrowTokenAdapter()
        binding.list.adapter = adapter

        lifecycleScope.launch {
            viewModel.data.collect { it ->
                adapter.submitList(it.getData())
            }
        }
        viewModel.loadData()
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.loadData()
            Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
        }, 600)
    }
}