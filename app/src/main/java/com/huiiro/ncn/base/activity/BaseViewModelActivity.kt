package com.huiiro.ncn.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.huiiro.ncn.util.ReflectUtils

open class BaseViewModelActivity<VB : ViewBinding> : BaseCommonActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ReflectUtils.newViewBinging(layoutInflater, javaClass)

        setContentView(binding.root)
    }
}