package com.smallraw.time.ui.recycleBin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smallraw.time.App
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper
import com.smallraw.time.model.thoroughDeleteTask
import com.smallraw.time.model.unDeleteTask
import com.smallraw.time.broadcast.RefreshMainDataReceiver
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.main.PreviewTaskActivity
import com.smallraw.time.utils.dateFormat
import com.smallraw.time.utils.dateParse
import com.smallraw.time.utils.differentDays
import kotlinx.android.synthetic.main.activity_preview_task.*
import kotlinx.android.synthetic.main.layout_task_card.*
import java.util.*

class RecycleBinManagerActivity : BaseTitleBarActivity() {
    companion object {
        @JvmStatic
        private val EXTER_DATA = "exter_data"

        @JvmStatic
        val EXTER_DATA_POSITION = "exter_data_position"

        @JvmStatic
        val REQUEST_CODE_PREVIEW = 1

        fun start(obj: Any, date: MemorialEntity, position: Int, requesCode: Int) {
            if (obj is Activity) {
                val intent = Intent(obj, RecycleBinManagerActivity::class.java)
                intent.putExtra(RecycleBinManagerActivity.EXTER_DATA, date)
                intent.putExtra(RecycleBinManagerActivity.EXTER_DATA_POSITION, position)
                obj.startActivityForResult(intent, requesCode)
            } else if (obj is Fragment) {
                val intent = Intent(obj.context, RecycleBinManagerActivity::class.java)
                intent.putExtra(RecycleBinManagerActivity.EXTER_DATA, date)
                intent.putExtra(RecycleBinManagerActivity.EXTER_DATA_POSITION, position)
                obj.startActivityForResult(intent, requesCode)
            } else if (obj is android.app.Fragment) {
                val intent = Intent(obj.activity, RecycleBinManagerActivity::class.java)
                intent.putExtra(RecycleBinManagerActivity.EXTER_DATA, date)
                intent.putExtra(RecycleBinManagerActivity.EXTER_DATA_POSITION, position)
                obj.startActivityForResult(intent, requesCode)
            }
        }
    }

    private lateinit var mMemorialEntity: MemorialEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_bin_manager)
        initTitleRight()
        (application as App).getAppExecutors().diskIO().execute {
            initData()
            (application as App).getAppExecutors().mainThread().execute {
                setViewData(mMemorialEntity)
            }
        }
    }

    override fun selfTitleContent(): String {
        return getString(R.string.title_recycle_bin)
    }

    private fun initTitleRight() {
        val right = newTitleRightView()
        addRightView(right)
    }

    private fun newTitleRightView(): View {
        val right = ImageView(this)
        val layoutParams = ViewGroup.LayoutParams(RudenessScreenHelper.pt2px(this, 35F).toInt(), RudenessScreenHelper.pt2px(this, 35F).toInt())
        right.layoutParams = layoutParams
        right.setBackgroundResource(R.drawable.ic_recovery_black)
        right.setOnClickListener {
            (application as App).getAppExecutors().diskIO().execute {
                unDeleteTask(application, mMemorialEntity)
                RefreshMainDataReceiver.sendBroadcast(application)
                (application as App).getAppExecutors().mainThread().execute {
                    val intent = Intent()
                    intent.putExtra(PreviewTaskActivity.EXTER_DATA_POSITION, getIntent().getIntExtra(PreviewTaskActivity.EXTER_DATA_POSITION, -1))
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
        return right
    }

    private fun initData() {
        mMemorialEntity = intent.getParcelableExtra<MemorialEntity>(RecycleBinManagerActivity.EXTER_DATA)
        mMemorialEntity = (application as App).getRepository().getTask(mMemorialEntity.id)
    }

    private fun setViewData(memorial: MemorialEntity) {
        tv_title.text = memorial.name
        tv_content.text = memorial.description

        val mCurrentDate = dateParse(dateFormat(Date()))
        if (memorial.type == 0) {
            val days = differentDays(Date(), memorial.beginTime)
            tv_day.text = "${Math.abs(days)}"
            tv_day_hint.text = "累计"
        } else {
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

        val color = Color.parseColor(memorial.color)
        layout_card_root.background = ColorDrawable(color)
        tv_delete.setTextColor(color)
        tv_delete.text = "彻底删除"
    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_delete -> {
                (application as App).getAppExecutors().diskIO().execute {
                    thoroughDeleteTask(application, mMemorialEntity)
                }
            }
            else -> {
            }
        }
        val intent = Intent()
        intent.putExtra(PreviewTaskActivity.EXTER_DATA_POSITION, getIntent().getIntExtra(PreviewTaskActivity.EXTER_DATA_POSITION, -1))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
