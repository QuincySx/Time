package com.smallraw.time.ui.taskInfo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.shareCard.ShareCardActivity
import com.smallraw.time.utils.dateFormat
import com.smallraw.time.utils.dateParse
import com.smallraw.time.utils.differentDays
import kotlinx.android.synthetic.main.activity_task_info.*
import java.util.*

class TaskInfoActivity : BaseTitleBarActivity(), View.OnClickListener {
    companion object {
        @JvmStatic
        private val EXTER_DATA = "exter_data"

        fun start(context: Context, date: MemorialEntity) {
            val intent = Intent(context, TaskInfoActivity::class.java)
            intent.putExtra(EXTER_DATA, date)
            context.startActivity(intent)
        }
    }

    private lateinit var mMemorialEntity: MemorialEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_info)
        setTitleBarLeftImage(R.drawable.ic_back_white)
        initTitleRight()
        initData()
        setViewData(mMemorialEntity)
    }

    private fun initData() {
        mMemorialEntity = intent.getParcelableExtra<MemorialEntity>(EXTER_DATA)
    }

    private fun initTitleRight() {
        val right = newTitleRightView()
        right.findViewById<ImageView>(R.id.img_title_setting).setOnClickListener(this)
        right.findViewById<ImageView>(R.id.img_title_share).setOnClickListener(this)
        addRightView(right)
    }

    override fun useStatusBarLightMode() = false

    override fun selfTitleBackgroundColor(): Int {
        return Color.parseColor("#EE386D")
    }

    private fun newTitleRightView(): View {
        val right = LayoutInflater.from(this).inflate(R.layout.layout_task_info_right_title, null, false);
        val layoutParams = ViewGroup.LayoutParams(RudenessScreenHelper.pt2px(this, 82F).toInt(), RudenessScreenHelper.pt2px(this, 35F).toInt())
        right.layoutParams = layoutParams
        return right
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.img_title_setting -> {

            }
            R.id.img_title_share -> {
                ShareCardActivity.start(this, mMemorialEntity)
            }
        }
    }

    private fun setViewData(memorial: MemorialEntity) {
        tv_title.text = memorial.name
        tv_content.text = memorial.description

        val mCurrentDate = dateParse(dateFormat(Date()))
        if (memorial.type == 0) {
            val days = differentDays(Date(), memorial.beginTime)
            tv_day.text = "${Math.abs(days)}"
//            tvType.text = "累计日"
            tv_day_hint.text = "累计"
        } else {
//            tvType.text = "倒数日"
            if (mCurrentDate < memorial.beginTime) {
                val days = differentDays(Date(), memorial.beginTime)
                tv_day.text = "${Math.abs(days)}"
                tv_day_hint.text = "剩余"
            } else if (mCurrentDate <= memorial.endTime || (memorial.endTime == memorial.beginTime && mCurrentDate == memorial.beginTime)) {
                val days = differentDays(Date(), memorial.beginTime)
                tv_day.text = "${Math.abs(days + 1)}"
                tv_day_hint.text = "活动中"
            } else {
                val days = differentDays(memorial.endTime, Date())
                tv_day.text = "${Math.abs(days)}"
                tv_day_hint.text = "已过"
            }
        }

        if (memorial.type == 0) {
            tv_time.text = "${dateFormat(memorial.beginTime)}"
        } else {
            tv_time.text = "${dateFormat(memorial.beginTime)} — ${dateFormat(memorial.endTime)}"
        }
    }
}
