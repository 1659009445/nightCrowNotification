package com.huiiro.ncn.base.fragment.dialog

import android.view.View
import androidx.annotation.IdRes

abstract class BaseCommonDialogFragment : BaseDialogFragment() {

    /**
     * 重写父类 findViewById 便于调用
     */
    fun <T : View?> findViewById(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }
}