package com.huiiro.ncn.component.tab.index.content.fn1BuildCrow

import BuildTokenLineChatView
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.huiiro.ncn.R
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
                val beginTime = it.getData()?.day_begin_time?.let { TimeUtils.getSimpleTime(it) }
                    ?: "12-12 12:00"
                val endTime =
                    it.getData()?.day_end_time?.let { TimeUtils.getSimpleTime(it) } ?: "12-12 12:00"

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

                //图表
                val dayHourDetails = it.getData()?.day_hour_details
                val tradeDetails = it.getData()?.day_trade_details

                val lineEntries = mutableListOf<Entry>()
                val barEntries = mutableListOf<BarEntry>()

                val labels = mutableListOf<String>()
                val crowVolume = mutableListOf<String>()

                if (dayHourDetails != null) {
                    for ((index, detail) in dayHourDetails.withIndex()) {
                        detail.p?.let { price ->
                            lineEntries.add(Entry(index.toFloat(), price.toFloat()))
                            //use regex
                            //val timeWithoutSeconds = detail.time?.replace(Regex(":\\d{2}$"), "") ?: ""
                            //labels.add(timeWithoutSeconds)
                            labels.add(detail.time?.substring(0, 5) ?: "")

                        }
                    }
                }

                if (tradeDetails != null) {
                    for ((index, detail) in tradeDetails.withIndex()) {
                        detail.v?.let { volume ->
                            barEntries.add(BarEntry(index.toFloat(), volume.toFloat()))
                            crowVolume.add((detail.w ?: "").toString())
                        }
                    }
                }

                val lineDataSet = LineDataSet(lineEntries, "Crow Price")
                val barDataSet = BarDataSet(barEntries, "Crow Volume")

                val lineData = LineData(lineDataSet)
                val barData = BarData(barDataSet)

                binding.lineChart.data = lineData
                binding.barChart.data = barData

                //build line
                val lineXAxis = binding.lineChart.xAxis
                lineXAxis.valueFormatter = IndexAxisValueFormatter(labels)
                lineXAxis.position = XAxis.XAxisPosition.BOTTOM
                lineXAxis.granularity = 1f
                lineXAxis.isGranularityEnabled = true

                val lineCharMarkerView = BuildTokenLineChatView(
                    requireContext(),
                    R.layout.component_index_build_token_line_chart,
                    labels
                )

                binding.lineChart.marker = lineCharMarkerView
                binding.lineChart.invalidate()

                //build bar
                val barXAxis = binding.barChart.xAxis
                barXAxis.valueFormatter = IndexAxisValueFormatter(labels)
                barXAxis.position = XAxis.XAxisPosition.BOTTOM
                barXAxis.granularity = 1f
                barXAxis.isGranularityEnabled = true

                val buildTokenBarChatView = BuildTokenBarChatView(
                    requireContext(),
                    R.layout.component_index_build_token_bar_chart,
                    labels,
                    crowVolume
                )

                binding.barChart.marker = buildTokenBarChatView
                binding.barChart.invalidate()
            }
        }

        viewModel.loadData()
    }
}