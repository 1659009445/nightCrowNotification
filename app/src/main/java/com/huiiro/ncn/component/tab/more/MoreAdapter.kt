package com.huiiro.ncn.component.tab.more

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.huiiro.ncn.component.tab.notice.NoticeAdapter
import com.huiiro.ncn.databinding.ComponentMoreItemBinding
import com.huiiro.ncn.domain.common.MoreEntity
import com.huiiro.ncn.util.ImageUtils

class MoreAdapter : BaseQuickAdapter<MoreEntity, MoreAdapter.ViewHolder>() {

    var onItemClick: ((MoreEntity) -> Unit)? = null

    companion object {
        const val TAG = "MoreAdapter"
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: MoreEntity?) {
        holder.bindData(item!!)
        holder.itemView.setOnClickListener {
            Log.d(NoticeAdapter.TAG, "onBindViewHolder: article clicked, aid: ${item.action}")
            onItemClick?.invoke(item)
        }
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
            binding.noticeDesc.text = moreEntity.desc
            ImageUtils.showImage(binding.noticeImage, moreEntity.image)
        }
    }
}