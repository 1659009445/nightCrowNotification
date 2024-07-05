package com.huiiro.ncn.component.tab.index.content.fn1BuildCrow.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.huiiro.ncn.R

@SuppressLint("ViewConstructor")
class BuildTokenBarChatView(
    context: Context,
    layoutResource: Int,
    private val timeLabels: List<String>,
    private val crowLabels: List<String>
) : MarkerView(context, layoutResource) {

    private val tvTime: TextView = findViewById(R.id.tvTime)
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private val tvCrow: TextView = findViewById(R.id.tvCrow)

    @SuppressLint("DefaultLocale")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e != null && highlight != null) {
            val index = e.x.toInt()
            val time = timeLabels.getOrNull(index) ?: "N/A"
            val crow = crowLabels.getOrNull(index)
            tvTime.text = String.format("Time: %s", time)
            tvCrow.text = String.format("Crow: %s", crow)
            tvContent.text = String.format("Value: %.4f", e.y)

        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}