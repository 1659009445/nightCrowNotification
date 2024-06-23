package com.huiiro.ncn.base.adapter

import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.huiiro.ncn.component.tab.index.IndexFragment

/**
 * tab viewpager2 绑定指示器
 */
class TabLayoutViewPager2Mediator(
    private val indicator: TabLayout,
    private val pager: ViewPager2,
    private val config: ((indicator: TabLayout, pager: ViewPager2) -> Unit)? = null
) {
    private val map = mutableMapOf<MenuItem, Int>()

    init {
        val data = IndexFragment.getIndexIndicatorCategories()
        val count = data.size
        for (i in 0 until count) {
            indicator.addTab(indicator.newTab().setText(data[i].title), false)
        }

        indicator.selectTab(indicator.getTabAt(0))
    }

    fun attach() {
        config?.invoke(indicator, pager)
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.selectTab(indicator.getTabAt(position))
            }
        })

        indicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val index = tab?.position
                if (pager.currentItem != index) {
                    if (index != null) {
                        pager.setCurrentItem(index, false)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

}