package com.huiiro.ncn.util

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.huiiro.ncn.R

object ImageUtils {

    @SuppressLint("CheckResult")
    fun showImage(view: ImageView, data: String?, defRes: Int = R.drawable.res_pic_basic_load) {
        if (null == data || "" == data) {
            view.setImageResource(defRes)
        } else {
            Glide.with(view).load(data).apply {
                //enable disk cache
                //diskCacheStrategy(DiskCacheStrategy.ALL)
                placeholder(R.drawable.res_pic_basic_load)
                error(R.drawable.res_pic_basic_error)
            }.into(view)
        }
    }
}