package com.lixinxinlove.notelove.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import kotlinx.android.synthetic.main.activity_tu_jia_home.*

class TuJiaHomeActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_tu_jia_home
    }

    override fun listener() {
        llLayout.setOnTouchListener(MyOnTouchListener())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    inner class MyOnTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event!!.actionMasked) {
                MotionEvent.ACTION_DOWN -> {

                    Toast.makeText(mContext, "按下", Toast.LENGTH_SHORT).show()
                }
                MotionEvent.ACTION_MOVE -> {
                    Toast.makeText(mContext, "移动", Toast.LENGTH_SHORT).show()
                }
                MotionEvent.ACTION_UP -> {
                    moveToTop()
                    Toast.makeText(mContext, "抬起", Toast.LENGTH_SHORT).show()
                }
            }
            return true
        }
    }



    private fun moveToTop() {
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(llMain, "translationY", 100f, 0f)
        animator.duration = 2000
        animator.start()
    }


}
