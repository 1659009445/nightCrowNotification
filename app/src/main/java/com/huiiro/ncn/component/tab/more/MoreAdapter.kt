package com.huiiro.ncn.component.tab.more

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.huiiro.ncn.databinding.ComponentMoreItemBinding
import com.huiiro.ncn.domain.common.MoreEntity

class MoreAdapter : BaseQuickAdapter<MoreEntity, MoreAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: MoreEntity?) {
        holder.bindData(item!!)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ComponentMoreItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    class ViewHolder(val binding: ComponentMoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(moreEntity: MoreEntity) {
            binding.noticeTitle.text = moreEntity.title
        }
    }
}