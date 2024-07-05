package com.huiiro.ncn.component.tab.index.content.fn2CrowToken

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.huiiro.ncn.R
import com.huiiro.ncn.component.tab.index.content.fn2CrowToken.view.CrowTokenLineChatView
import com.huiiro.ncn.databinding.ComponentIndexTokenItemBinding
import com.huiiro.ncn.domain.TokenChartEntity
import com.huiiro.ncn.domain.TokenEntity
import com.huiiro.ncn.util.ImageUtils
import java.text.SimpleDateFormat
import java.util.Locale

class CrowTokenAdapter : BaseQuickAdapter<TokenEntity, CrowTokenAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: TokenEntity?) {
        holder.bindData(item!!, holder.itemView.context)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ComponentIndexTokenItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    class ViewHolder(val binding: ComponentIndexTokenItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun bindData(tokenEntity: TokenEntity, context: Context) {
            val textColor = when (tokenEntity.priceStatus) {
                "U" -> ContextCompat.getColor(binding.root.context, R.color.up)
                "D" -> ContextCompat.getColor(binding.root.context, R.color.down)
                "S" -> ContextCompat.getColor(binding.root.context, R.color.black)
                else -> ContextCompat.getColor(binding.root.context, R.color.black)
            }

            val textSymbol = when (tokenEntity.priceStatus) {
                "U" -> "+"
                "D" -> "-"
                else -> ""
            }

            binding.contentText.text = tokenEntity.symbol
            ImageUtils.showImage(binding.contentIcon, tokenEntity.icon)

            //近期实时参考价
            binding.priceClose.text = "${tokenEntity.close} CROW"
            binding.priceClose.setTextColor(textColor)
            binding.priceCloseDollar.text = "$ " + String.format("%.2f", tokenEntity.closeDollar)
            binding.priceCloseDollar.setTextColor(textColor)

            //近期价格变化
            if (tokenEntity.price24hChanged!! > 0) {
                binding.priceChanged.text = "+" + String.format("%.2f", tokenEntity.price24hChanged)
            } else {
                binding.priceChanged.text = String.format("%.2f", tokenEntity.price24hChanged)
            }

            binding.priceChanged.setTextColor(textColor)
            binding.pricePercent.text = "$textSymbol${tokenEntity.price24hPercent}%"
            binding.pricePercent.setTextColor(textColor)

            binding.high.text = tokenEntity.high.toString()
            binding.low.text = tokenEntity.low.toString()
            binding.low.text = tokenEntity.low.toString()
            binding.volume.text = tokenEntity.tradedVolume.toString()
            binding.volumeCrow.text = tokenEntity.volume.toString()


            val charts: List<TokenChartEntity> = tokenEntity.charts

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val newDateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
            val formattedTimes = charts.map { tokenChartEntity ->
                try {
                    val date = tokenChartEntity.time?.let { dateFormat.parse(it) }
                    newDateFormat.format(date!!)
                } catch (e: Exception) {
                    tokenChartEntity.time
                }
            }

            val hEntries = charts.mapIndexed { index, tokenChartEntity ->
                Entry(index.toFloat(), tokenChartEntity.h?.toFloat() ?: 0f).apply {
                    data = tokenChartEntity
                }
            }
            val lEntries = charts.mapIndexed { index, tokenChartEntity ->
                Entry(index.toFloat(), tokenChartEntity.l?.toFloat() ?: 0f).apply {
                    data = tokenChartEntity
                }
            }
            val cEntries = charts.mapIndexed { index, tokenChartEntity ->
                Entry(index.toFloat(), tokenChartEntity.o?.toFloat() ?: 0f).apply {
                    data = tokenChartEntity
                }
            }
            val vEntries = charts.mapIndexed { index, tokenChartEntity ->
                Entry(index.toFloat(), tokenChartEntity.v?.toFloat() ?: 0f).apply {
                    data = tokenChartEntity
                }
            }

            val hLineDataSet = LineDataSet(hEntries, "High Price (h)").apply {
                color = Color.RED
                valueTextColor = Color.BLACK
            }
            val lLineDataSet = LineDataSet(lEntries, "Low Price (l)").apply {
                color = Color.GREEN
                valueTextColor = Color.BLACK
            }
            val cLineDataSet = LineDataSet(cEntries, "Close Price (c)").apply {
                color = Color.YELLOW
                valueTextColor = Color.BLACK
            }
            val vLineDataSet = LineDataSet(vEntries, "Volume (v)").apply {
                color = Color.CYAN
                valueTextColor = Color.BLACK
            }

            val lineData = LineData(hLineDataSet, lLineDataSet, cLineDataSet, vLineDataSet)

            val lineCharMarkerView = CrowTokenLineChatView(context, R.layout.component_index_token_line_chart)
            binding.lineChart.marker = lineCharMarkerView
            binding.lineChart.data = lineData

            binding.lineChart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                valueFormatter = IndexAxisValueFormatter(formattedTimes)
            }

            binding.lineChart.axisLeft.setDrawGridLines(false)
            binding.lineChart.axisRight.isEnabled = false

            binding.lineChart.invalidate()

        }
    }
}