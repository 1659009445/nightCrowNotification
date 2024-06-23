package com.huiiro.ncn.component.guide

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.huiiro.ncn.base.adapter.BaseFragmentAdapter

class GuideAdapter(fragmentActivity: FragmentActivity) :
    BaseFragmentAdapter<Int>(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return GuideFragment.newInstance(getData(position))
    }

}
