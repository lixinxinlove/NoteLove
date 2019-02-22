package com.lixinxinlove.notelove.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.adapter.ListDialogAdapter
import kotlinx.android.synthetic.main.dialog_list_view.*

class ListDialog(@param:NonNull private val mContext: Context) :
    Dialog(mContext, R.style.ListDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_list_view)

        val window = window
        val params = window!!.attributes
        params.gravity = Gravity.CENTER
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.decorView.setPadding(60, 0, 60, 0)
        window.attributes = params
        //  getWindow().setWindowAnimations(R.style.BottomToTopAnim);
        findView()
        setListener4View()
    }


    private fun findView() {
        var data = List(20, init = { index -> "lixinxin ${index}" })
        DialogRecyclerView.layoutManager = LinearLayoutManager(mContext)
        var mAdapter = ListDialogAdapter(data)
        DialogRecyclerView.adapter = mAdapter
    }

    private fun setListener4View() {

    }

}