package com.huiiro.ncn.component.tab.index

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.huiiro.ncn.component.tab.index.content.fn1BuildCrow.BuildTokenFragment
import com.huiiro.ncn.component.tab.index.content.fn2CrowToken.CrowTokenFragment
import com.huiiro.ncn.component.tab.index.content.fn3DigitalToken.DigitalTokenFragment
import com.huiiro.ncn.domain.common.Category

class IndexAdapter(fragmentActivity: FragmentActivity, private val datum: List<Category>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return datum.size
    }

    override fun createFragment(position: Int): Fragment {
        //获取内容fragment
        //0 铸币信息页
        //1 游戏货币页
        //2 数字货币页
        val id = datum[position].id;
        return when (position) {
            0 -> BuildTokenFragment.newInstance(id)
            1 -> CrowTokenFragment.newInstance(id)
            2 -> DigitalTokenFragment.newInstance(id)
            else -> BuildTokenFragment.newInstance(id)
        }
    }
}