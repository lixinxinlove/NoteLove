package com.kotlin.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/*
    Glide工具类
 */
object GlideUtils {

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.e("GlideUtils", "onLoadCleared")
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    Log.e("GlideUtils", "加载完成")
                    imageView.setImageDrawable(resource)
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    Log.e("GlideUtils", "开始加载")
                }

            })

    }
}
