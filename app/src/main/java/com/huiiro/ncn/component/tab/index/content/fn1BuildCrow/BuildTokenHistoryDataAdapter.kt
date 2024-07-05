package com.huiiro.ncn.component.tab.index.content.fn1BuildCrow

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.huiiro.ncn.R
import com.huiiro.ncn.databinding.ComponentIndexBuildTokenHistoryDataBinding
import com.huiiro.ncn.domain.CrowPriceChangeHistory

class BuildTokenHistoryDataAdapter :
    BaseQuickAdapter<CrowPriceChangeHistory, BuildTokenHistoryDataAdapter.ViewHolder>() {

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        item: CrowPriceChangeHistory?
    ) {
        holder.bindData(item!!)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ComponentIndexBuildTokenHistoryDataBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    class ViewHolder(val binding: ComponentIndexBuildTokenHistoryDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun bindData(data: CrowPriceChangeHistory) {
            val color = when (data.status) {
                "U" -> ContextCompat.getColor(binding.root.context, R.color.up)
                "D" -> ContextCompat.getColor(binding.root.context, R.color.down)
                "S" -> ContextCompat.getColor(binding.root.context, R.color.black)
                else -> ContextCompat.getColor(binding.root.context, R.color.black)
            }

            val symbol = when (data.status) {
                "U" -> "+"
                "D" -> "-"
                else -> ""
            }
            binding.historyValue.text = "$ " + data.v.toString()
            binding.changeValue.text = symbol + String.format("%.4f", data.c)
            binding.changeValue.setTextColor(color)

            binding.historyTime.text = data.time
        }
    }
}