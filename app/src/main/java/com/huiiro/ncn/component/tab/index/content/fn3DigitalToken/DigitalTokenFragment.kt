package com.huiiro.ncn.component.tab.index.content.fn3DigitalToken

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.component.tab.index.content.fn3DigitalToken.view.DigitalTokenViewModel
import com.huiiro.ncn.databinding.IndexContentDigitalTokenBinding
import com.huiiro.ncn.util.FormatUtils
import kotlinx.coroutines.launch

class DigitalTokenFragment : BaseViewModelFragment<IndexContentDigitalTokenBinding>() {

    private lateinit var viewModel: DigitalTokenViewModel

    companion object {
        fun newInstance(categoryId: String? = null): DigitalTokenFragment {
            val args = Bundle()

            categoryId?.let { args.putString(Constant.ID, categoryId) }

            val fragment = DigitalTokenFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViewData() {
        super.initViewData()
        binding.swipeRefreshLayout.setOnRefreshListener { refreshData() }
    }

    @SuppressLint("SetTextI18n")
    override fun initDatum() {
        super.initDatum()
        writeDataView()
    }

    override fun initListener() {
        super.initListener()
        binding.buttonToTop.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, 0)
        }

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.scrollView.scrollY
            if (scrollY > 0) {
                binding.buttonToTop.visibility = View.VISIBLE
            } else {
                binding.buttonToTop.visibility = View.GONE
            }
        }
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.loadData()
            Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
        }, 600)
    }

    private fun writeDataView() {
        viewModel = ViewModelProvider(this)[DigitalTokenViewModel::class.java]
        lifecycleScope.launch {
            viewModel.data.collect { it ->

                //price
                binding.price.text = it.getData()?.price?.let {
                    FormatUtils.formatDouble(it, 12)
                }

                //hour
                binding.oneHourPrice.text = it.getData()?.oneHour?.priceChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }

                //day
                binding.oneDayPrice.text = it.getData()?.oneDay?.priceChange?.let {
                    FormatUtils.formatDouble(it, 4)
                }
                binding.oneDayLiquidity.text = it.getData()?.oneDay?.liquidityChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneDayVolume.text = it.getData()?.oneDay?.volume?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneDayVolumeChange.text = it.getData()?.oneDay?.volumeChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }

                //week
                binding.oneWeekPrice.text = it.getData()?.oneWeek?.priceChange?.let {
                    FormatUtils.formatDouble(it, 4)
                }
                binding.oneWeekLiquidity.text = it.getData()?.oneWeek?.liquidityChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneWeekVolume.text = it.getData()?.oneWeek?.volume?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneWeekVolumeChange.text = it.getData()?.oneWeek?.volumeChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }

                //month
                binding.oneMonthPrice.text = it.getData()?.oneMonth?.priceChange?.let {
                    FormatUtils.formatDouble(it, 4)
                }
                binding.oneMonthLiquidity.text = it.getData()?.oneMonth?.liquidityChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneMonthVolume.text = it.getData()?.oneMonth?.volume?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneMonthVolumeChange.text = it.getData()?.oneMonth?.volumeChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }

                //year
                binding.oneYearPrice.text = it.getData()?.oneYear?.priceChange?.let {
                    FormatUtils.formatDouble(it, 4)
                }
                binding.oneYearLiquidity.text = it.getData()?.oneYear?.liquidityChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneYearVolume.text = it.getData()?.oneYear?.volume?.let {
                    FormatUtils.formatDouble(it, 6)
                }
                binding.oneYearVolumeChange.text = it.getData()?.oneYear?.volumeChange?.let {
                    FormatUtils.formatDouble(it, 6)
                }
            }
        }
        viewModel.loadData()
    }
}