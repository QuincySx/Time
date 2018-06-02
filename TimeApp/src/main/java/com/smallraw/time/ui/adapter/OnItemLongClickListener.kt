package com.smallraw.time.ui.adapter

import android.support.v7.widget.RecyclerView
import com.smallraw.time.db.entity.MemorialEntity

interface OnItemLongClickListener {
    fun onLongClick(position: Int, holder: RecyclerView.ViewHolder, item: MemorialEntity): Boolean
}