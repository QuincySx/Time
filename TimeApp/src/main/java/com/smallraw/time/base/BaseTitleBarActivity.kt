package com.smallraw.time.base

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintLayout
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.smallraw.time.R
import kotlinx.android.synthetic.main.activity_base_title_activity.*


abstract class BaseTitleBarActivity : BaseActivity() {
    protected lateinit var mImgTitleBarLeft: ImageView
    protected lateinit var mTvTitleBarContent: TextView
    protected lateinit var mLayoutTitleBarRight: LinearLayout
    protected lateinit var mLayoutTitleBar: ConstraintLayout
    protected lateinit var mLayoutContent: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base_title_activity)

        mLayoutTitleBar = layout_title_bar
        mLayoutTitleBarRight = layout_title_bar_right
        mImgTitleBarLeft = img_title_bar_left
        mTvTitleBarContent = tv_title_bar_content
        mLayoutContent = root_content

        mImgTitleBarLeft.setOnClickListener(selfLeftClickListener())
        mTvTitleBarContent.setText(selfTitleContent())
        mLayoutTitleBar.setBackgroundColor(selfTitleBackgroundColor())
    }

    protected fun addRightView(view: View) {
        mLayoutTitleBarRight.addView(view)
    }

    @ColorInt
    protected open fun selfTitleBackgroundColor(): Int {
        return Color.WHITE
    }

    protected open fun selfTitleContent(): String {
        return ""
    }

    protected open fun selfLeftClickListener(): View.OnClickListener? {
        return View.OnClickListener {
            finish()
        }
    }

    override fun setContentView(layoutResID: Int) {
        mLayoutContent.removeAllViews()
        View.inflate(this, layoutResID, mLayoutContent)
        onContentChanged()
    }

    override fun setContentView(view: View) {
        mLayoutContent.removeAllViews()
        mLayoutContent.addView(view)
        onContentChanged()
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        mLayoutContent.removeAllViews()
        mLayoutContent.addView(view, params)
        onContentChanged()
    }

    protected fun setTitleBarLeftImage(@DrawableRes res: Int) {
        val drawable = ResourcesCompat.getDrawable(resources, res, null)
        mImgTitleBarLeft.setImageDrawable(drawable)
    }
}