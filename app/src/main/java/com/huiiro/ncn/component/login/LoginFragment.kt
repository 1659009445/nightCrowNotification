package com.huiiro.ncn.component.login

import android.os.Bundle
import com.huiiro.ncn.base.fragment.BaseViewModelFragment
import com.huiiro.ncn.databinding.LoginFragmentBinding

/**
 * 登录界面
 */
class LoginFragment : BaseViewModelFragment<LoginFragmentBinding>() {

    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }
}