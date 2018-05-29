package com.smallraw.time.ui.taskinfo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper

class TaskInfoActivity : BaseTitleBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_info)
        setTitleBarLeftImage(R.drawable.ic_back_white)
        initTitleRight();
    }

    private fun initTitleRight() {
        val right = newTitleRightView()
        addRightView(right)
    }

    override fun selfTitleBackgroundColor(): Int {
        return Color.parseColor("#EE386D")
    }

    private fun newTitleRightView(): View {
        val right = LayoutInflater.from(this).inflate(R.layout.layout_task_info_right_title, null, false);
        val layoutParams = ViewGroup.LayoutParams(RudenessScreenHelper.pt2px(this, 82F).toInt(), RudenessScreenHelper.pt2px(this, 35F).toInt())
        right.layoutParams = layoutParams
        return right;
    }
}
