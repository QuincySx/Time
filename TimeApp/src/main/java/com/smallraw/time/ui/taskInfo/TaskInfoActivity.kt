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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_info)
        setTitleBarLeftImage(R.drawable.ic_back_white)
        initTitleRight()
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
                val intent = Intent(this, ShareCardActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
