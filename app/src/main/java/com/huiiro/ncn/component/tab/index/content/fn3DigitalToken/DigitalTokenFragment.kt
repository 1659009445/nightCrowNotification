package com.huiiro.ncn.component.tab.index.content.fn3DigitalToken

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.IndexContentDigitalTokenBinding
import kotlinx.coroutines.launch

class DigitalTokenFragment : BaseViewModelFragment<IndexContentDigitalTokenBinding>() {

    private lateinit var viewModel: DigitalTokenViewModel

    override fun initDatum() {
        super.initDatum()

        viewModel = ViewModelProvider(this)[DigitalTokenViewModel::class.java]

        lifecycleScope.launch {
            viewModel.data.collect {

            }
        }

        viewModel.loadData()

    }

    companion object {

        fun newInstance(categoryId: String? = null): DigitalTokenFragment {
            val args = Bundle()

            categoryId?.let { args.putString(Constant.ID, categoryId) }

            val fragment = DigitalTokenFragment()
            fragment.arguments = args
            return fragment
        }
    }
}