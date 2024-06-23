package com.huiiro.ncn.component.tab.index.content.fn1BuildCrow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.ComponentCrowPriceBinding
import com.huiiro.ncn.databinding.IndexContentBuildCrowBinding
import com.huiiro.ncn.util.TimeUtils
import kotlinx.coroutines.launch

class BuildTokenFragment : BaseViewModelFragment<IndexContentBuildCrowBinding>() {

    private lateinit var viewModel: BuildTokenViewModel

    companion object {
        fun newInstance(categoryId: String? = null): BuildTokenFragment {
            val args = Bundle()

            categoryId?.let { args.putString(Constant.ID, categoryId) }

            val fragment = BuildTokenFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViewData() {
        super.initViewData()
        binding.swipeRefreshLayout.setOnRefreshListener { refreshData() }
    }

    override fun initDatum() {
        super.initDatum()
        writeDataView()
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.loadData()
            Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
        }, 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun writeDataView() {
        viewModel = ViewModelProvider(this)[BuildTokenViewModel::class.java]

        //write in mvc
        //lifecycleScope.launch { CrowRepository.crow() }

        //write in mvvm
        lifecycleScope.launch {
            viewModel.data.collect { it ->
                //更新时间
                val updateTime =
                    it.getData()?.update_time?.let { TimeUtils.getSimpleTimeWithSecond(it) }
                        ?: "12-12 12:00"
                binding.updateTime.text = "最后更新于：${updateTime}"

                //统计时间
                val beginTime =
                    it.getData()?.day_begin_time?.let { TimeUtils.getSimpleTime(it) }
                        ?: "12-12 12:00"
                val endTime =
                    it.getData()?.day_end_time?.let { TimeUtils.getSimpleTime(it) }
                        ?: "12-12 12:00"

                binding.countTime.text = "$beginTime    -    $endTime"

                //精确值
                binding.dayActualPrice.text = "$ " + it.getData()?.day_accurate_price.toString()

                //日最小值
                val dayMinValue = ComponentCrowPriceBinding.bind(binding.dayMinPrice)
                dayMinValue.priceValue.text = "$ " + it.getData()?.day_min_price.toString()
                dayMinValue.priceLabel.text = "最小值（\$）"
                //日平均值
                val dayAvgValue = ComponentCrowPriceBinding.bind(binding.dayAvgPrice)
                dayAvgValue.priceValue.text = "$ " + it.getData()?.day_average_price.toString()
                dayAvgValue.priceLabel.text = "平均值（\$）"
                //日最大值
                val dayMaxValue = ComponentCrowPriceBinding.bind(binding.dayMaxPrice)
                dayMaxValue.priceValue.text = "$ " + it.getData()?.day_max_price.toString()
                dayMaxValue.priceLabel.text = "最大值（\$）"


                //时最小值
                val hourMinValue = ComponentCrowPriceBinding.bind(binding.hourMinPrice)
                hourMinValue.priceValue.text = "$ " + it.getData()?.hour_min_price.toString()
                hourMinValue.priceLabel.text = "最小值（\$）"
                //时平均值
                val hourAvgValue = ComponentCrowPriceBinding.bind(binding.hourAvgPrice)
                hourAvgValue.priceValue.text = "$ " + it.getData()?.hour_average_price.toString()
                hourAvgValue.priceLabel.text = "平均值（\$）"
                //日最大值
                val hourMaxValue = ComponentCrowPriceBinding.bind(binding.hourMaxPrice)
                hourMaxValue.priceValue.text = "$ " + it.getData()?.hour_max_price.toString()
                hourMaxValue.priceLabel.text = "最大值（\$）"
            }
        }

        viewModel.loadData()
    }
}