package com.huiiro.ncn.component.tab.index.content.fn1BuildCrow

import com.huiiro.ncn.component.tab.index.content.fn1BuildCrow.view.BuildTokenLineChatView
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.huiiro.ncn.component.tab.index.content.fn1BuildCrow.view.BuildTokenBarChatView
import com.huiiro.ncn.component.tab.index.content.fn1BuildCrow.view.BuildTokenViewModel
import com.huiiro.ncn.databinding.ComponentCrowPriceBinding
import com.huiiro.ncn.databinding.IndexContentBuildCrowBinding
import com.huiiro.ncn.util.FormatUtils
import kotlinx.coroutines.launch

class BuildTokenFragment : BaseViewModelFragment<IndexContentBuildCrowBinding>() {

    private lateinit var viewModel: BuildTokenViewModel
    private lateinit var adapter: BuildTokenHistoryDataAdapter
    private lateinit var adapterWarning: BuildTokenWarningDataAdapter

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
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.warningList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

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
                    it.getData()?.updateTime?.let { FormatUtils.getSimpleTimeWithSecond(it) }
                        ?: "12-12 12:00"
                binding.updateTime.text = "最后更新于：${updateTime}"

                //统计时间
                val beginTime = it.getData()?.dayBeginTime?.let { FormatUtils.getSimpleTime(it) }
                    ?: "12-12 12:00"
                val endTime = it.getData()?.dayEndTime?.let { FormatUtils.getSimpleTime(it) }
                    ?: "12-12 12:00"
                binding.countTime.text = "$beginTime    -    $endTime"

                //精确值
                binding.dayActualPrice.text = "$ " + it.getData()?.dayAccuratePrice
                    ?.let { FormatUtils.formatDouble(it, 12) }

                //日最小值
                val dayMinValue = ComponentCrowPriceBinding.bind(binding.dayMinPrice)
                dayMinValue.priceValue.text = "$ " + it.getData()?.dayMinPrice
                    ?.let { FormatUtils.formatDouble(it, 4) }
                dayMinValue.priceLabel.text = "最小值（\$）"

                //日平均值
                val dayAvgValue = ComponentCrowPriceBinding.bind(binding.dayAvgPrice)
                dayAvgValue.priceValue.text = "$ " + it.getData()?.dayAveragePrice
                    ?.let { FormatUtils.formatDouble(it, 4) }
                dayAvgValue.priceLabel.text = "平均值（\$）"

                //日最大值
                val dayMaxValue = ComponentCrowPriceBinding.bind(binding.dayMaxPrice)
                dayMaxValue.priceValue.text = "$ " + it.getData()?.dayMaxPrice
                    ?.let { FormatUtils.formatDouble(it, 4) }
                dayMaxValue.priceLabel.text = "最大值（\$）"


                //时最小值
                val hourMinValue = ComponentCrowPriceBinding.bind(binding.hourMinPrice)
                hourMinValue.priceValue.text = "$ " + it.getData()?.hourMinPrice
                    ?.let { FormatUtils.formatDouble(it, 4) }
                hourMinValue.priceLabel.text = "最小值（\$）"

                //时平均值
                val hourAvgValue = ComponentCrowPriceBinding.bind(binding.hourAvgPrice)
                hourAvgValue.priceValue.text = "$ " + it.getData()?.hourAveragePrice
                    ?.let { FormatUtils.formatDouble(it, 4) }
                hourAvgValue.priceLabel.text = "平均值（\$）"

                //日最大值
                val hourMaxValue = ComponentCrowPriceBinding.bind(binding.hourMaxPrice)
                hourMaxValue.priceValue.text = "$ " + it.getData()?.hourMaxPrice
                    ?.let { FormatUtils.formatDouble(it, 4) }
                hourMaxValue.priceLabel.text = "最大值（\$）"


                //折线图表
                val dayHourDetails = it.getData()?.dayHourDetails
                val lineEntries = mutableListOf<Entry>()
                val labels = mutableListOf<String>()

                if (dayHourDetails != null) {
                    for ((index, detail) in dayHourDetails.withIndex()) {
                        detail.p?.let { price ->
                            lineEntries.add(Entry(index.toFloat(), price.toFloat()))
                            labels.add(detail.time?.substring(0, 5) ?: "")

                        }
                    }
                }

                val lineDataSet = LineDataSet(lineEntries, "Crow Price")
                val lineData = LineData(lineDataSet)
                binding.lineChart.data = lineData

                val lineXAxis = binding.lineChart.xAxis
                lineXAxis.valueFormatter = IndexAxisValueFormatter(labels)
                lineXAxis.position = XAxis.XAxisPosition.BOTTOM
                lineXAxis.granularity = 1f
                lineXAxis.isGranularityEnabled = true
                lineXAxis.setDrawGridLines(false)

                val lineCharMarkerView = BuildTokenLineChatView(
                    requireContext(),
                    R.layout.component_index_build_token_line_chart,
                    labels
                )

                binding.lineChart.marker = lineCharMarkerView
                binding.lineChart.axisLeft.setDrawGridLines(false)
                binding.lineChart.axisRight.isEnabled = true
                binding.lineChart.invalidate()


                //柱状图表
                val tradeDetails = it.getData()?.dayTradeDetails
                val barEntries = mutableListOf<BarEntry>()
                val crowVolume = mutableListOf<String>()

                if (tradeDetails != null) {
                    for ((index, detail) in tradeDetails.withIndex()) {
                        detail.v?.let { volume ->
                            barEntries.add(BarEntry(index.toFloat(), volume.toFloat()))
                            crowVolume.add((detail.w ?: "").toString())
                        }
                    }
                }

                val barDataSet = BarDataSet(barEntries, "Crow Volume")
                val barData = BarData(barDataSet)
                binding.barChart.data = barData

                val barXAxis = binding.barChart.xAxis
                barXAxis.valueFormatter = IndexAxisValueFormatter(labels)
                barXAxis.position = XAxis.XAxisPosition.BOTTOM
                barXAxis.granularity = 1f
                barXAxis.isGranularityEnabled = true
                barXAxis.setDrawGridLines(false)

                val buildTokenBarChatView = BuildTokenBarChatView(
                    requireContext(),
                    R.layout.component_index_build_token_bar_chart,
                    labels,
                    crowVolume
                )

                binding.barChart.marker = buildTokenBarChatView
                binding.lineChart.axisLeft.setDrawGridLines(false)
                binding.lineChart.axisRight.isEnabled = true
                binding.barChart.invalidate()

                //v 1.0.9
                adapterWarning = BuildTokenWarningDataAdapter()
                binding.warningList.adapter = adapterWarning
                val warningHistory = it.getData()?.warningHistory?.take(10)
                adapterWarning.submitList(warningHistory)

                adapter = BuildTokenHistoryDataAdapter()
                binding.list.adapter = adapter
                val histories = it.getData()?.changeHistory?.take(100)
                adapter.submitList(histories)
            }
        }
        viewModel.loadData()
    }
}