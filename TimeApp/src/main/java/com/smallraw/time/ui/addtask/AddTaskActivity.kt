package com.smallraw.time.ui.addtask

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper

class AddTaskActivity : BaseTitleBarActivity() {
    companion object {
        /**
         * 添加的 Task 类型
         */
        private val EXTRA_TASK_TYPE = "extra_task_type"

        /**
         * 倒数日
         */
        @JvmField
        val EXTRA_TASK_TYPE_RECIPROCAL = 1

        /**
         * 累计日
         */
        @JvmField
        val EXTRA_TASK_TYPE_ACCUMULATIVE = 2

        @JvmStatic
        fun startReciprocal(context: Context) {
            start(context, EXTRA_TASK_TYPE_RECIPROCAL)
        }

        @JvmStatic
        fun startAccumulative(context: Context) {
            start(context, EXTRA_TASK_TYPE_ACCUMULATIVE)
        }

        @JvmStatic
        fun start(context: Context, taskType: Int) {
            val intent = Intent(context, AddTaskActivity::class.java)
            intent.putExtra(EXTRA_TASK_TYPE, taskType)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleBarLeftImage(R.drawable.ic_back_white)
        setContentView(R.layout.activity_add_task)
        initTitleRight()
    }

    override fun useStatusBarLightMode() = false

    private fun initTitleRight() {
        val rightView = LayoutInflater.from(this).inflate(R.layout.layout_add_task_title_content, null, false)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, RudenessScreenHelper.pt2px(this, 35F).toInt())
        rightView.layoutParams = layoutParams
        addRightView(rightView)
    }

    override fun selfTitleBackgroundColor(): Int {
        return getContentColor()
    }

    fun getContentColor(): Int {
        val taskType = intent.getIntExtra(EXTRA_TASK_TYPE, 1)
        val parseColor: Int
        if (taskType == 2) {
            parseColor = Color.parseColor("#139EED")
        } else {
            parseColor = Color.parseColor("#EE386D")
        }
        return parseColor
    }
}
