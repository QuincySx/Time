package com.smallraw.time.ui.shareCard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.utils.dateFormat
import com.smallraw.time.utils.dateParse
import com.smallraw.time.utils.differentDays
import kotlinx.android.synthetic.main.activity_task_info.*
import java.util.*

class ShareCardActivity : BaseTitleBarActivity() {
    companion object {
        @JvmStatic
        private val EXTER_DATA = "exter_data"

        fun start(context: Context, date: MemorialEntity) {
            val intent = Intent(context, ShareCardActivity::class.java)
            intent.putExtra(EXTER_DATA, date)
            context.startActivity(intent)
        }
    }

    private lateinit var mMemorialEntity: MemorialEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_card)
        initTitleRight()
        initData()
        setViewData(mMemorialEntity)
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

    private fun initData() {
        mMemorialEntity = intent.getParcelableExtra<MemorialEntity>(ShareCardActivity.EXTER_DATA)
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
