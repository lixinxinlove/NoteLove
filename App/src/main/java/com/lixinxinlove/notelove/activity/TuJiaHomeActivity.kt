package com.lixinxinlove.notelove.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import com.kotlin.base.utils.GlideUtils
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import kotlinx.android.synthetic.main.activity_tu_jia_home.*

class TuJiaHomeActivity : BaseActivity() {

    private val url = "http://pic3.nipic.com/20090701/1242397_161620025_2.jpg"

    private lateinit var animator: ObjectAnimator

    override fun layoutId(): Int {
        return R.layout.activity_tu_jia_home
    }

    override fun listener() {
        llLayout.setOnTouchListener(MyOnTouchListener())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        GlideUtils.load(this, url, mImageView)

    }


    inner class MyOnTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event!!.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    moveToTop()
                }
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                    animator.reverse()
                }
            }
            return true
        }
    }


    private fun moveToTop() {
        animator = ObjectAnimator.ofFloat(llMain, "translationY", 0f, 1000f)
        animator.duration = 2000
        animator.interpolator = BounceInterpolator()
        animator.start()
    }


}
