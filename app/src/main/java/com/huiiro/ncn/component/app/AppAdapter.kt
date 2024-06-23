package com.huiiro.ncn.component.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.huiiro.ncn.component.tab.index.IndexFragment
import com.huiiro.ncn.component.tab.more.MoreFragment
import com.huiiro.ncn.component.tab.my.MyFragment
import com.huiiro.ncn.component.tab.notice.NoticeFragment

class AppAdapter(fragmentActivity: FragmentActivity, private val count: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IndexFragment.newInstance()
            1 -> NoticeFragment.newInstance()
            2 -> MoreFragment.newInstance()
            3 -> MyFragment.newInstance()
            else -> IndexFragment.newInstance()
        }
    }
}