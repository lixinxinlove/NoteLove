package com.kotlin.base.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/*
    Glide工具类
 */
object GlideUtils {

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }
}
