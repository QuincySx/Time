package com.smallraw.time.ui.main

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
import com.smallraw.time.biz.*
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.utils.dateFormat
import com.smallraw.time.utils.dateParse
import com.smallraw.time.utils.differentDays
import kotlinx.android.synthetic.main.activity_preview_task.*
import kotlinx.android.synthetic.main.layout_task_card.*
import java.util.*

class PreviewTaskActivity : BaseTitleBarActivity() {
    companion object {
        @JvmStatic
        private val EXTER_DATA = "exter_data"

        @JvmStatic
        val EXTER_DATA_POSITION = "exter_data_position"

        @JvmStatic
        val REQUEST_CODE_PREVIEW = 1

        fun start(obj: Any, date: MemorialEntity, requesCode: Int) {
            start(obj, date, -1, requesCode)
        }

        fun start(obj: Any, date: MemorialEntity, position: Int, requesCode: Int) {
            if (obj is Activity) {
                val intent = Intent(obj, PreviewTaskActivity::class.java)
                intent.putExtra(EXTER_DATA, date)
                intent.putExtra(EXTER_DATA_POSITION, position)
                obj.startActivityForResult(intent, requesCode)
            } else if (obj is Fragment) {
                val intent = Intent(obj.context, PreviewTaskActivity::class.java)
                intent.putExtra(EXTER_DATA, date)
                intent.putExtra(EXTER_DATA_POSITION, position)
                obj.startActivityForResult(intent, requesCode)
            } else if (obj is android.app.Fragment) {
                val intent = Intent(obj.activity, PreviewTaskActivity::class.java)
                intent.putExtra(EXTER_DATA, date)
                intent.putExtra(EXTER_DATA_POSITION, position)
                obj.startActivityForResult(intent, requesCode)
            }
        }
    }

    private lateinit var mMemorialEntity: MemorialEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_task)

        initTitleRight()
        (application as App).getAppExecutors().diskIO().execute {
            initData()
            (application as App).getAppExecutors().mainThread().execute {
                setViewData(mMemorialEntity)
            }
        }
    }

    private fun initTitleRight() {
        val right = newTitleRightView()
        addRightView(right)
    }

    private fun newTitleRightView(): View {
        val right = ImageView(this)
        val layoutParams = ViewGroup.LayoutParams(RudenessScreenHelper.pt2px(this, 35F).toInt(), RudenessScreenHelper.pt2px(this, 35F).toInt())
        right.layoutParams = layoutParams
        right.setBackgroundResource(R.drawable.ic_setting_black)
        return right
    }

    private fun initData() {
        mMemorialEntity = intent.getParcelableExtra<MemorialEntity>(PreviewTaskActivity.EXTER_DATA)
        mMemorialEntity = (application as App).getRepository().getTask(mMemorialEntity.id)
    }

    override fun selfTitleContent(): String {
        return getString(R.string.title_preview)
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

        val color = Color.parseColor(memorial.color)
        layout_card_root.background = ColorDrawable(color)
        tv_delete.setTextColor(color)
        tv_top.setTextColor(color)
        tv_archiving.setTextColor(color)

        refreshDeleteView(mMemorialEntity)
        refreshTopView(mMemorialEntity)
        refreshArchivingView(mMemorialEntity)
    }

    private fun refreshDeleteView(memorialEntity: MemorialEntity) {
        (application as App).getAppExecutors().mainThread().execute {
            if (memorialEntity.isStrike) {
                tv_delete.text = "恢复"
            } else {
                tv_delete.text = "删除"
            }
        }
    }

    private fun refreshTopView(memorialEntity: MemorialEntity) {
        (application as App).getAppExecutors().diskIO().execute {
            if (isTopTask(application, mMemorialEntity)) {
                (application as App).getAppExecutors().mainThread().execute {
                    tv_top.text = "取消置顶"
                }
            } else {
                (application as App).getAppExecutors().mainThread().execute {
                    tv_top.text = "置顶"
                }
            }
        }
    }

    private fun refreshArchivingView(memorialEntity: MemorialEntity) {
        (application as App).getAppExecutors().mainThread().execute {
            if (memorialEntity.isArchive) {
                tv_archiving.text = "取消归档"
            } else {
                tv_archiving.text = "归档"
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_delete -> {
                (application as App).getAppExecutors().diskIO().execute {
                    if (mMemorialEntity.isStrike) {
                        unDeleteTask(application, mMemorialEntity)
                    } else {
                        deleteTask(application, mMemorialEntity)

                    }
                    refreshDeleteView(mMemorialEntity)
                }
            }
            R.id.tv_top -> {
                (application as App).getAppExecutors().diskIO().execute {
                    if (isTopTask(application, mMemorialEntity)) {
                        unTopTask(application, mMemorialEntity)
                    } else {
                        topTask(application, mMemorialEntity)
                    }
                    refreshTopView(mMemorialEntity)
                }
            }
            R.id.tv_archiving -> {
                (application as App).getAppExecutors().diskIO().execute {
                    if (mMemorialEntity.isArchive) {
                        unArchivingTask(application, mMemorialEntity)
                    } else {
                        archivingTask(application, mMemorialEntity)
                    }
                    refreshArchivingView(mMemorialEntity)
                }
            }
            else -> {
            }
        }
        val intent = Intent()
        intent.putExtra(EXTER_DATA_POSITION, getIntent().getIntExtra(EXTER_DATA_POSITION, -1))
        setResult(Activity.RESULT_OK, intent)
    }

}
