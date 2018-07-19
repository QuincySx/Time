package com.smallraw.time.ui.main

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainDrawerLayout : DrawerLayout {
    var viewPager: ViewPager? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val viewGroupCardView = getChildAt(0) as ViewGroup
        val viewGroupCoordinatorLayout = viewGroupCardView.getChildAt(0) as ViewGroup
        viewPager = viewGroupCoordinatorLayout.getChildAt(2) as ViewPager
    }

    var mLastX = -1f
    var mLastY = -1f
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y
        var intercept = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = x
                mLastY = y
                intercept = true
            }
            MotionEvent.ACTION_MOVE -> {
                var intervalX = x - mLastX
                if (mLastX == -1f) {
                    intervalX = 0f
                }
                var intervalY = y - mLastY
                if (mLastY == -1f) {
                    intervalY = 0f
                }

                Log.e("==onInterceptEvent==", "${intervalX}")

                if (intervalX > intervalY) {
                    //左右滑动
                    val currentItem = viewPager?.currentItem
                    if (intervalX > 0) {
                        //向右滑动
                        if (currentItem == 0) {
                            //可以滑
                            intercept = true
                        }
                    }
                }

                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                mLastX = -1f
                mLastY = -1f
            }
            MotionEvent.ACTION_CANCEL -> {
                mLastX = -1f
                mLastY = -1f
            }
        }
        if (!intercept) {
            return intercept
        }
        return super.onInterceptTouchEvent(event)
    }
}
