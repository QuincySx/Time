package com.smallraw.time.ui.about

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseTitleBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initTitleRight()
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        tv_app_version.text = packageInfo.versionName
    }

    override fun selfTitleContent(): String {
        return getString(R.string.title_about)
    }

    private fun initTitleRight() {
        val right = newTitleRightView()
        addRightView(right)
    }

    private fun newTitleRightView(): View {
        val right = ImageView(this)
        val layoutParams = ViewGroup.LayoutParams(RudenessScreenHelper.pt2px(this, 35F).toInt(), RudenessScreenHelper.pt2px(this, 35F).toInt())
        right.layoutParams = layoutParams
        right.setBackgroundResource(R.drawable.ic_more_black)
        return right;
    }
}
