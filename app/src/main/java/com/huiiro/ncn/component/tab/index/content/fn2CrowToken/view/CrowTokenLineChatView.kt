package com.huiiro.ncn.component.tab.index.content.fn2CrowToken.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.huiiro.ncn.R
import com.huiiro.ncn.domain.TokenChartEntity

@SuppressLint("ViewConstructor")
class CrowTokenLineChatView(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {

    private val tkVolume: TextView = findViewById(R.id.tkVolume)
    private val tkHigh: TextView = findViewById(R.id.tkHigh)
    private val tkLow: TextView = findViewById(R.id.tkLow)
    private val tkClose: TextView = findViewById(R.id.tkClose)
    private val tkTime: TextView = findViewById(R.id.tkTime)

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            val tokenChartEntity = e.data as? TokenChartEntity
            tokenChartEntity?.let { entity ->
                tkVolume.text = "Volume: ${entity.v}"
                tkHigh.text = "High: ${entity.h}"
                tkLow.text = "Low: ${entity.l}"
                tkClose.text = "Close: ${entity.c}"
                tkTime.text = "Time: ${entity.time}"
            }
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}


