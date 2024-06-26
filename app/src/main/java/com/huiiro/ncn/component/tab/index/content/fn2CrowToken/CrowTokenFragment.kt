package com.huiiro.ncn.component.tab.index.content.fn2CrowToken

import android.os.Bundle
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
            viewModel.data.collect {
                adapter.submitList(it.getData())
            }
        }
        viewModel.loadData()
    }
}