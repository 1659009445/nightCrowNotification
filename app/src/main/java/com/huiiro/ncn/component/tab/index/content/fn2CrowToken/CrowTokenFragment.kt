package com.huiiro.ncn.component.tab.index.content.fn2CrowToken

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.IndexContentCrowTokenBinding
import kotlinx.coroutines.launch

class CrowTokenFragment : BaseViewModelFragment<IndexContentCrowTokenBinding>() {

    private lateinit var viewModel: TokenCrowViewModel

    override fun initDatum() {
        super.initDatum()

        viewModel = ViewModelProvider(this)[TokenCrowViewModel::class.java]

        lifecycleScope.launch {
            viewModel.data.collect {

            }
        }

        viewModel.loadData()
    }

    companion object {

        fun newInstance(categoryId: String? = null): CrowTokenFragment {
            val args = Bundle()

            categoryId?.let { args.putString(Constant.ID, categoryId) }

            val fragment = CrowTokenFragment()
            fragment.arguments = args
            return fragment
        }
    }
}