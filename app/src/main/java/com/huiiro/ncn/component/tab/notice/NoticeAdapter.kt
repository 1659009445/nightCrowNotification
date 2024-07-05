package com.huiiro.ncn.component.tab.notice

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.huiiro.ncn.databinding.ComponentNoticeItemBinding
import com.huiiro.ncn.domain.NoticeEntity

class NoticeAdapter : BaseQuickAdapter<NoticeEntity, NoticeAdapter.ViewHolder>() {

    var onItemClick: ((NoticeEntity) -> Unit)? = null

    companion object {
        const val TAG = "NoticeAdapter"
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: NoticeEntity?) {
        holder.bindData(item!!)
        holder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: article clicked, aid: ${item.aid}")
            onItemClick?.invoke(item)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ComponentNoticeItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    class ViewHolder(val binding: ComponentNoticeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(tokenEntity: NoticeEntity) {
            binding.noticeTitle.text = tokenEntity.title
            binding.noticeTime.text = tokenEntity.start

        }
    }
}