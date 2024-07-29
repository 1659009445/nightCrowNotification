package com.huiiro.ncn.component.tab.index.content.fn1BuildCrow

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.huiiro.ncn.databinding.ComponentIndexBuildTokenWarningDataBinding
import com.huiiro.ncn.domain.CrowWarningHistoryEntity

class BuildTokenWarningDataAdapter :
    BaseQuickAdapter<CrowWarningHistoryEntity, BuildTokenWarningDataAdapter.ViewHolder>() {

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        item: CrowWarningHistoryEntity?
    ) {
        holder.bindData(item!!)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ComponentIndexBuildTokenWarningDataBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    class ViewHolder(val binding: ComponentIndexBuildTokenWarningDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(data: CrowWarningHistoryEntity) {
            binding.warningPrice.text = data.warning.toString()
            binding.currentValue.text = "$ " + data.current.toString()
            binding.warningTime.text = data.time
        }
    }
}