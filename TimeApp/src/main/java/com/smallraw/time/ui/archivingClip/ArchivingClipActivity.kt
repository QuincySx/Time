package com.smallraw.time.ui.archivingClip

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper

class ArchivingClipActivity : BaseTitleBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archiving_clip)
        initTitleRight()
    }

    override fun selfTitleContent(): String {
        return getString(R.string.title_archiving_clip)
    }

    private fun initTitleRight() {
        val right = newTitleRightView()
        addRightView(right)
    }

    private fun newTitleRightView(): View {
        val right = ImageView(this)
        val layoutParams = ViewGroup.LayoutParams(RudenessScreenHelper.pt2px(this, 35F).toInt(), RudenessScreenHelper.pt2px(this, 35F).toInt())
        right.layoutParams = layoutParams
        right.setBackgroundResource(R.drawable.ic_delete_black)
        return right
    }
}
