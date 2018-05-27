package com.smallraw.time.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.View
import com.smallraw.time.R
import com.smallraw.time.base.BaseActivity
import com.smallraw.time.ui.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawerView()
        initViewPager()
    }

    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(TaskListFragment())
        viewPagerAdapter.addFragment(AddTaskOptionFragment())
        view_pager.adapter = viewPagerAdapter
    }

    private fun initDrawerView() {
        layout_drawer.setScrimColor(Color.TRANSPARENT);
        layout_drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val measuredWidth = layout_slip.measuredWidth
                val measuredHeight = layout_slip.measuredHeight
                layout_content.translationX = measuredWidth * slideOffset / 1.25F
                layout_content.scaleY = 1 - 0.2F * slideOffset
                layout_content.scaleX = 1 - 0.2F * slideOffset
                layout_content.translationY = measuredHeight / 30 * slideOffset
            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }
        })
    }


}
