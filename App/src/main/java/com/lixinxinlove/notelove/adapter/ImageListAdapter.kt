package com.lixinxinlove.notelove.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lixinxinlove.base.utils.DateTimeUtils
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.data.protocol.Note

/**
 * 列表 adapter
 */
class ImageListAdapter(data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image_list, data) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.tv_info, item)

        helper!!.addOnClickListener(R.id.llDelete)
        helper!!.addOnClickListener(R.id.llItem)
    }
}