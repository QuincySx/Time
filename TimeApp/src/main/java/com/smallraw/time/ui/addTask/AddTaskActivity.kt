package com.smallraw.time.ui.addTask

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.smallraw.time.App
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.entity.Weather
import com.smallraw.time.model.BaseCallback
import com.smallraw.time.model.WeatherModel
import com.smallraw.time.ui.taskInfo.TaskInfoActivity
import com.smallraw.time.utils.dateParse
import com.smallraw.time.utils.getWeekOfDate
import com.smallraw.time.utils.monthDayFormat
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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
        fun startReciprocal(any: Any, requestCode: Int) {
            start(any, EXTRA_TASK_TYPE_RECIPROCAL, requestCode)
        }

        @JvmStatic
        fun startAccumulative(any: Any, requestCode: Int) {
            start(any, EXTRA_TASK_TYPE_ACCUMULATIVE, requestCode)
        }

        @JvmStatic
        fun start(any: Any, taskType: Int, requestCode: Int) {
            val intent: Intent
            if (any is Activity) {
                intent = Intent(any, AddTaskActivity::class.java)
                intent.putExtra(EXTRA_TASK_TYPE, taskType)
                any.startActivityForResult(intent, requestCode)
            } else if (any is Fragment) {
                intent = Intent(any.activity, AddTaskActivity::class.java)
                intent.putExtra(EXTRA_TASK_TYPE, taskType)
                any.startActivityForResult(intent, requestCode)
            } else if (any is android.app.Fragment) {
                intent = Intent(any.activity, AddTaskActivity::class.java)
                intent.putExtra(EXTRA_TASK_TYPE, taskType)
                any.startActivityForResult(intent, requestCode)
            }
        }
    }

    var mSelectColor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleBarLeftImage(R.drawable.ic_back_white)
        setContentView(R.layout.activity_add_task)
        initTitleRight()
        initTimeDate()
        initActivityType()
        initBackgroundColorListener()
    }

    private fun initTimeDate() {
        val instance = Calendar.getInstance()
        val year = instance.get(Calendar.YEAR)
        val month = instance.get(Calendar.MONTH)
        val dayOfMonth = instance.get(Calendar.DAY_OF_MONTH)
        tv_end_date.setText(splicingTime(year, month, dayOfMonth))
        tv_begin_date.setText(splicingTime(year, month, dayOfMonth))
    }

    override fun useStatusBarLightMode() = false

    private fun initTitleRight() {
        val rightView = LayoutInflater.from(this).inflate(R.layout.layout_add_task_title_content, null, false)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, RudenessScreenHelper.pt2px(this, 35F).toInt())
        rightView.layoutParams = layoutParams
        addRightView(rightView)
        tv_date.text = monthDayFormat(Date())
        tv_week.text = getWeekOfDate(this, Date())

        WeatherModel().getWeatherCache(object : BaseCallback<Weather> {
            override fun onSuccess(data: Weather) {
                tv_weather.text = "${data.cond_txt} · ${data.tmp}°C"
            }

            override fun onError(e: Exception) {
                tv_weather.text = "暂无 · 0°C"
            }
        })
    }

    override fun selfTitleBackgroundColor(): Int {
        return getContentColor()
    }

    private fun initActivityType() {
        initBackgroundColor()
        val taskType = intent.getIntExtra(EXTRA_TASK_TYPE, 1)
        if (taskType == 1) {
            radio_color_ee386d.isChecked = true
        } else {
            hideEndDate()
            radio_color_139eed.isChecked = true
            edit_title_name.hint = "累计日名称"
        }
    }

    private fun hideEndDate() {
        tv_end_date.visibility = View.GONE
        tv_hine_end_date.visibility = View.GONE
        view_separate_2.visibility = View.GONE
    }

    private fun initBackgroundColor() {
        layout_root.background = ColorDrawable(getContentColor())
    }

    private fun initBackgroundColorListener() {
        radio_color_group.setOnCheckedChangeListener { group, checkedId ->
            mSelectColor = switchColor(checkedId)
            val parseColor = Color.parseColor(mSelectColor)
            layout_root.background = ColorDrawable(parseColor)
            mLayoutTitleBar.setBackgroundColor(parseColor)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_save -> {
                saveDate()
            }
            R.id.tv_end_date -> {
                startDateCheck(object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        tv_end_date.setText(splicingTime(year, month, dayOfMonth))
                    }
                })
            }
            R.id.tv_begin_date -> {
                startDateCheck(object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        tv_begin_date.setText(splicingTime(year, month, dayOfMonth))
                    }
                })
            }
        }
    }

    private fun saveDate() {
        if (checkData()) {
            //0 累计日  1 倒数日
            var taskType = intent.getIntExtra(EXTRA_TASK_TYPE, 1)
            if (taskType == 2) {
                taskType = 0
            }
            val endDateParse = dateParse(tv_end_date.text.toString())
            val begindateParse = dateParse(tv_begin_date.text.toString())
            val memorialEntity = MemorialEntity(
                    edit_title_name.text.toString().trim(),
                    edit_remarks.text.toString().trim(),
                    taskType,
                    mSelectColor,
                    begindateParse,
                    endDateParse,
                    Date()
            )
            (application as App).getAppExecutors().diskIO().execute({
                val insertTaskId = (application as App).getRepository().insertTask(memorialEntity)
                memorialEntity.id = insertTaskId
                TaskInfoActivity.start(this, memorialEntity)
                setResult(Activity.RESULT_OK)
                finish()
            })
        }
    }

    private fun checkData(): Boolean {
        if (edit_title_name == null || edit_title_name.text.toString().trim().equals("")) {
            Toast.makeText(this, "请填写名称！", Toast.LENGTH_SHORT).show()
            return false
        }
        val taskType = intent.getIntExtra(EXTRA_TASK_TYPE, 1)
        if (taskType == 1) {
            val endDateParse = dateParse(tv_end_date.text.toString())
            val begindateParse = dateParse(tv_begin_date.text.toString())

            if (endDateParse < begindateParse) {
                Toast.makeText(this, "结束时间不能早于开始时间！", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun splicingTime(year: Int, month: Int, dayOfMonth: Int): String {
        return "${year}-${formatDate(month + 1)}-${formatDate(dayOfMonth)}"
    }

    private fun formatDate(int: Int): String {
        if (int < 10) {
            return "0$int"
        } else {
            return "$int"
        }
    }

    private fun startDateCheck(onDateCallback: DatePickerDialog.OnDateSetListener) {
        val instance = Calendar.getInstance()
        val year = instance.get(Calendar.YEAR)
        val month = instance.get(Calendar.MONTH)
        val day = instance.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this,
                onDateCallback, year, month, day)
                .show()
    }

    private fun getContentColor(): Int {
        val taskType = intent.getIntExtra(EXTRA_TASK_TYPE, 1)
        val parseColor: Int
        if (taskType == 2) {
            parseColor = Color.parseColor("#139EED")
            mSelectColor = "#139EED"
        } else {
            parseColor = Color.parseColor("#EE386D")
            mSelectColor = "#EE386D"
        }
        return parseColor
    }

    private fun switchColor(id: Int): String {
        when (id) {
            R.id.radio_color_ee386d -> {
                return "#ee386d"
            }
            R.id.radio_color_139eed -> {
                return "#139eed"
            }
            R.id.radio_color_ffc529 -> {
                return "#ffc529"
            }
            R.id.radio_color_9092a5 -> {
                return "#9092a5"
            }
            R.id.radio_color_ff8e9f -> {
                return "#ff8e9f"
            }
            R.id.radio_color_2b0050 -> {
                return "#2b0050"
            }
            R.id.radio_color_fd92c4 -> {
                return "#fd92c4"
            }
            else -> {
                return "#ee386d"
            }
        }
    }
}
