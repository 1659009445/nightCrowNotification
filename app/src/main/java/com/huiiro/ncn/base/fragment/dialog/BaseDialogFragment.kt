package com.huiiro.ncn.base.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 * 基类对话框
 */
abstract class BaseDialogFragment : DialogFragment() {

    /**
     * 初始化控件
     */
    protected open fun initViewData() {}

    /**
     * 初始化数据
     */
    protected open fun initDatum() {}

    /**
     * 初始化监听器
     */
    protected open fun initListener() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getLayoutView(inflater, container, savedInstanceState)
    }

    abstract fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewData()
        initDatum()
        initListener()
    }
}