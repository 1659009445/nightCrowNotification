package com.huiiro.ncn.component.tab.notice

import android.text.Html
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.base.activity.BaseViewModelActivity
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.databinding.NoticeDetailActivityBinding
import kotlinx.coroutines.launch

class NoticeDetailActivity : BaseViewModelActivity<NoticeDetailActivityBinding>() {

    private lateinit var viewModel: NoticeDetailViewModel

    companion object {
        const val TAG = "NoticeDetailActivity"
    }

    override fun initViewData() {
        super.initViewData()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initDatum() {
        super.initDatum()
        val noticeId = intent.getIntExtra(Constant.NOTICE_ID, -1)
        Log.d(TAG, "initViewData: receive noticeId: $noticeId")

        viewModel = ViewModelProvider(this)[NoticeDetailViewModel::class.java]

        lifecycleScope.launch {
            viewModel.data.collect {
                binding.noticeTitle.text = it.getData()?.title
                val content = it.getData()?.content ?: ""
                // 使用 Html.fromHtml 解析 HTML 内容
                binding.noticeContent.text =
                    Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
            }
        }
        viewModel.loadData(noticeId)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}