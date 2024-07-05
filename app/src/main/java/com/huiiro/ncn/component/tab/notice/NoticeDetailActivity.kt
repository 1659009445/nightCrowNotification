package com.huiiro.ncn.component.tab.notice

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.util.Log
import android.view.View
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

    @SuppressLint("SetTextI18n")
    override fun initDatum() {
        super.initDatum()
        val noticeId = intent.getIntExtra(Constant.NOTICE_ID, -1)
        Log.d(TAG, "initViewData: receive noticeId: $noticeId")

        viewModel = ViewModelProvider(this)[NoticeDetailViewModel::class.java]

        lifecycleScope.launch {
            viewModel.data.collect {
                val content = it.getData()?.content ?: ""
                binding.noticePrev.text = it.getData()?.prevArticleId.toString()
                binding.noticeNext.text = it.getData()?.nextArticleId.toString()
                binding.noticeTitle.text = it.getData()?.title
                binding.noticeTime.text = it.getData()?.startedAt
                binding.noticeContent.text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
                binding.noticeUrl.text = it.getData()?.url
            }
        }
        viewModel.loadData(noticeId)
    }

    override fun initListener() {
        super.initListener()

        //back button
        binding.backButton.setOnClickListener {
            finish()
        }

        //click to redirect to origin web page
        binding.viewOriginalButton.setOnClickListener {
            val url = "https://www.nightcrows.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.noticeUrl.let { url }))
            startActivity(intent)
        }

        //click prev notice
        binding.viewPrevButton.setOnClickListener {
            val prevUrl = binding.noticePrev.text.toString()
            val intent = Intent(this, NoticeDetailActivity::class.java)
            intent.putExtra(Constant.NOTICE_ID, prevUrl.toIntOrNull() ?: -1)
            startActivity(intent)
        }

        //click next notice
        binding.viewNextButton.setOnClickListener {
            val nextUrl = binding.noticeNext.text.toString()
            val intent = Intent(this, NoticeDetailActivity::class.java)
            intent.putExtra(Constant.NOTICE_ID, nextUrl.toIntOrNull() ?: -1)
            startActivity(intent)
        }

        //handle visible problems
        binding.noticeContent.setOnClickListener {
            binding.controlButton.visibility = View.VISIBLE
            binding.backButton.visibility = View.VISIBLE
            binding.buttonToTop.visibility = View.VISIBLE
        }
        binding.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (binding.controlButton.visibility == View.GONE) {
                binding.controlButton.visibility = View.VISIBLE
                binding.backButton.visibility = View.VISIBLE
                binding.buttonToTop.visibility = View.VISIBLE
            }
        }

        //back top button
        binding.buttonToTop.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, 0)
        }
        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.scrollView.scrollY
            if (scrollY > 0) {
                binding.buttonToTop.visibility = View.VISIBLE
            } else {
                binding.buttonToTop.visibility = View.GONE
            }
        }
    }
}