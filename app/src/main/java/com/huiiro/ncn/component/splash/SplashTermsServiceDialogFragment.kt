package com.huiiro.ncn.component.splash

import android.os.Bundle
import android.os.Process
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.huiiro.ncn.R
import com.huiiro.ncn.base.fragment.dialog.BaseCommonDialogFragment
import com.huiiro.ncn.util.ScreenUtils
import com.huiiro.ncn.util.TextUtils

/**
 * 服务条款 && 隐私协议对话框
 */
class SplashTermsServiceDialogFragment : BaseCommonDialogFragment() {

    private lateinit var onClickAgree: View.OnClickListener
    private lateinit var contentView: TextView
    private lateinit var agreeView: TextView
    private lateinit var disagreeView: TextView

    override fun initViewData() {
        super.initViewData()

        //设置对话框不可点击空白取消
        isCancelable = false

        //修改<a>标签颜色
        contentView = findViewById(R.id.splash_privacy_content)
        TextUtils.setLinkColor(contentView, 0xFF00FF)

        //获取点击事件按钮
        agreeView = findViewById(R.id.splash_privacy_agree)
        disagreeView = findViewById(R.id.splash_privacy_disagree)

    }

    override fun initDatum() {
        super.initDatum()
        val content = Html.fromHtml(getString(R.string.privacy_content))
        contentView.text = content
    }

    override fun initListener() {
        super.initListener()

        //同意事件
        agreeView.setOnClickListener {
            dismiss()
            onClickAgree.onClick(it)
        }

        //不同意事件
        disagreeView.setOnClickListener {
            dismiss()
            Process.killProcess(Process.myPid())
        }
    }

    /**
     * 修改宽度
     */
    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (ScreenUtils.getScreenWidth(requireContext()) * 0.9).toInt()
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    /**
     * 获取view
     */
    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment_privacy, container, false)
    }

    /**
     * 加载对话框
     */
    companion object {
        fun show(fragmentManager: FragmentManager, onClickAgreeEvent: View.OnClickListener) {
            val dialogFragment = SplashTermsServiceDialogFragment()
            dialogFragment.onClickAgree = onClickAgreeEvent
            dialogFragment.show(fragmentManager, "SplashTermsServiceDialogFragment")
        }
    }
}