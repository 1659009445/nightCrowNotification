package com.huiiro.ncn.base.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class BaseFragmentAdapter<T>(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var datum: MutableList<T> = mutableListOf()

    override fun getItemCount(): Int {
        return datum.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDatum(datum: MutableList<T>) {
        this.datum.clear()
        this.datum.addAll(datum)
        notifyDataSetChanged()
    }

    fun getData(position: Int): T {
        return datum[position]
    }
}