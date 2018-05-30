package com.smallraw.time.ui.adapter

import android.support.v7.widget.RecyclerView

interface OnItemClickListener {
    fun onClick(position: Int, holder: RecyclerView.ViewHolder): Boolean
}