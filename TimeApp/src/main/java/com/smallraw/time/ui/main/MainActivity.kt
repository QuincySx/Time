package com.smallraw.time.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.smallraw.time.R
import com.smallraw.time.base.BaseActivity
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.about.AboutActivity
import com.smallraw.time.ui.adapter.OnItemLongClickListener
import com.smallraw.time.ui.adapter.ViewPagerAdapter
import com.smallraw.time.ui.archivingClip.ArchivingClipActivity
import com.smallraw.time.ui.recycleBin.RecycleBinActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    lateinit var mTaskListFragment: TaskListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawerView()
        initViewPager()
    }

    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        mTaskListFragment = TaskListFragment.newInstance(object : OnItemLongClickListener {
            override fun onLongClick(position: Int, holder: RecyclerView.ViewHolder): Boolean {
                val intent = Intent(this@MainActivity, PreviewTaskActivity::class.java)
                startActivity(intent)
                return true
            }
        }, object : TaskListFragment.DataRepositoryCallback {
            override fun getData() {
                initData()
            }
        })
        viewPagerAdapter.addFragment(mTaskListFragment)
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

    fun onDrawerClick(view: View) {
        var intent: Intent? = null
        when (view.id) {
            R.id.tv_archiving_clip -> {
                intent = Intent(this, ArchivingClipActivity::class.java)
            }
            R.id.tv_recycle_bin -> {
                intent = Intent(this, RecycleBinActivity::class.java)
            }
            R.id.tv_about -> {
                intent = Intent(this, AboutActivity::class.java)
            }
        }
        if (intent != null) {
            startActivity(intent)
        }
    }

    private fun initData() {
        val memorialList = ArrayList<MemorialEntity>()
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "139EED", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "9092A5", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "139EED", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "9092A5", Date(), Date(), Date()))
        memorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
        mTaskListFragment.addDate(memorialList)
    }
}
