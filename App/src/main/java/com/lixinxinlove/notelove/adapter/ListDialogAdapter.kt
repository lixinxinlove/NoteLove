package com.lixinxinlove.notelove.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lixinxinlove.notelove.R

class ListDialogAdapter(data: List<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_dialog_list, data) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.tvText, item)
    }

}