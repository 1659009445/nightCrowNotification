package com.huiiro.ncn.base.fragment

import android.view.View
import androidx.annotation.IdRes

abstract class BaseCommonFragment : BaseFragment() {

    /**
     * 重写父类 findViewById 便于调用
     */
    fun <T : View?> findViewById(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }
}