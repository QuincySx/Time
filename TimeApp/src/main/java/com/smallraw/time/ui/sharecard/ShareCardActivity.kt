package com.smallraw.time.ui.sharecard

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper

class ShareCardActivity : BaseTitleBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_card)
        initTitleRight()
    }

    private fun initTitleRight() {
        val right = newTitleRightView()
        addRightView(right)
    }

    override fun selfTitleContent(): String {
        return getString(R.string.title_share_card)
    }

    private fun newTitleRightView(): View {
        val right = ImageView(this)
        val layoutParams = ViewGroup.LayoutParams(RudenessScreenHelper.pt2px(this, 35F).toInt(), RudenessScreenHelper.pt2px(this, 35F).toInt())
        right.layoutParams = layoutParams
        right.setBackgroundResource(R.drawable.ic_share_black)
        return right;
    }
}
