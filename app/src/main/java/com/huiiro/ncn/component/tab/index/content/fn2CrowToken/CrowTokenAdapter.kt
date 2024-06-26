package com.huiiro.ncn.component.tab.index.content.fn2CrowToken

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.huiiro.ncn.R
import com.huiiro.ncn.databinding.ComponentIndexTokenItemBinding
import com.huiiro.ncn.databinding.IndexContentCrowTokenBinding
import com.huiiro.ncn.domain.TokenEntity
import com.huiiro.ncn.util.ImageUtils

class CrowTokenAdapter : BaseQuickAdapter<TokenEntity, CrowTokenAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: TokenEntity?) {
        holder.bindData(item!!)
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
        fun bindData(data: TokenEntity) {
            val color = when (data.price_status) {
                "U" -> ContextCompat.getColor(binding.root.context, R.color.up)
                "D" -> ContextCompat.getColor(binding.root.context, R.color.down)
                "S" -> ContextCompat.getColor(binding.root.context, R.color.black)
                else -> ContextCompat.getColor(binding.root.context, R.color.black)
            }

            val symbol = when (data.price_status) {
                "U" -> "+"
                "D" -> "-"
                else -> ""
            }

            binding.contentText.text = data.symbol
            ImageUtils.showImage(binding.contentIcon, data.icon)

            //近期实时参考价
            binding.priceClose.text = "${data.close} CROW"
            binding.priceClose.setTextColor(color)
            binding.priceCloseDollar.text = "$ " + String.format("%.2f", data.close_dollar)
            binding.priceCloseDollar.setTextColor(color)

            //近期价格变化
            if (data.price_24h_changed!! > 0) {
                binding.priceChanged.text = "+" + String.format("%.2f", data.price_24h_changed)
            } else {
                binding.priceChanged.text = String.format("%.2f", data.price_24h_changed)
            }

            binding.priceChanged.setTextColor(color)
            binding.pricePercent.text = "$symbol${data.price_24h_percent}%"
            binding.pricePercent.setTextColor(color)

            binding.high.text = data.high.toString()
            binding.low.text = data.low.toString()
            binding.low.text = data.low.toString()
            binding.volume.text = data.traded_volume.toString()
            binding.volumeCrow.text = data.volume.toString()

        }

    }
}