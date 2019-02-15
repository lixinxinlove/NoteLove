package com.lixinxinlove.notelove.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lixinxinlove.base.utils.DateTimeUtils
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.data.protocol.Note

/**
 * 列表 adapter
 */
class NoteListAdapter(data: MutableList<Note>?) :
    BaseQuickAdapter<Note, BaseViewHolder>(R.layout.item_note_list, data) {
    override fun convert(helper: BaseViewHolder?, item: Note?) {
        helper!!.setText(R.id.tv_info, item!!.info)
        helper!!.setText(R.id.tv_time, DateTimeUtils.timeForDate(item!!.time,DateTimeUtils.yyyy_MM_dd_HH_mm_ss))
    }
}