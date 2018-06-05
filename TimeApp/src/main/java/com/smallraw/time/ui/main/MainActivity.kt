package com.smallraw.time.ui.main

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.smallraw.time.App
import com.smallraw.time.R
import com.smallraw.time.base.BaseActivity
import com.smallraw.time.broadcast.RefreshMainDataReceiver
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.about.AboutActivity
import com.smallraw.time.ui.adapter.OnItemClickListener
import com.smallraw.time.ui.adapter.OnItemLongClickListener
import com.smallraw.time.ui.adapter.ViewPagerAdapter
import com.smallraw.time.ui.archivingClip.ArchivingClipActivity
import com.smallraw.time.ui.recycleBin.RecycleBinActivity
import com.smallraw.time.ui.taskInfo.TaskInfoActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.BroadcastReceiver
import android.content.Context


class MainActivity : BaseActivity() {
    lateinit var mTaskListFragment: TaskListFragment

    private var mDisplay = 0
    private var mOrder = 0

    private var localBroadcast: LocalBroadcastManager? = null
    private var localBroadcaseReceiver: RefreshMainDataReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawerView()
        initViewPager()
        initDiaplayClickListener()
        initOrderClickListener()
        initBroadcastReceiver()
    }

    private fun initBroadcastReceiver() {
        localBroadcast = LocalBroadcastManager.getInstance(this)
        //动态注册-都是一个套路
        val localFilter = IntentFilter()
        localFilter.addAction(RefreshMainDataReceiver.BroadcastRefreshMain)
        localBroadcaseReceiver = object : RefreshMainDataReceiver() {
            fun onReceive(context: Context, intent: Intent): Unit = initData()
        }
        localBroadcast?.registerReceiver(localBroadcaseReceiver!!, localFilter)
    }

    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        mTaskListFragment = TaskListFragment.newInstance(object : OnItemClickListener {
            override fun onClick(position: Int, holder: RecyclerView.ViewHolder, item: MemorialEntity) {
                TaskInfoActivity.start(this@MainActivity, item)
            }
        }, object : OnItemLongClickListener {
            override fun onLongClick(position: Int, holder: RecyclerView.ViewHolder, item: MemorialEntity): Boolean {
                PreviewTaskActivity.start(this@MainActivity, item, holder.layoutPosition, PreviewTaskActivity.REQUEST_CODE_PREVIEW)
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
        newData(mDisplay, mOrder)
    }

    private fun newData() {
        newData(mDisplay, mOrder)
    }

    private fun newData(display: Int, order: Int) {
        (application as App).getAppExecutors().networkIO().execute {
            val memorialList = (application as App).getRepository().getActiveTask(display, order)
            mTaskListFragment.newDate(memorialList)
        }
    }

    private fun initOrderClickListener() {
        rg_sort.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_def -> {
                    mOrder = 0
                }
                R.id.rb_time -> {
                    mOrder = 1
                }
                R.id.rb_color -> {
                    mOrder = 2
                }
                else -> {
                    mOrder = 0
                }
            }
            newData()
        }
    }

    private fun initDiaplayClickListener() {
        rg_display.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_all -> {
                    mDisplay = 0
                }
                R.id.rb_reciprocal -> {
                    mDisplay = 1
                }
                R.id.rb_accumulative -> {
                    mDisplay = 2
                }
                else -> {
                    mDisplay = 0
                }
            }
            newData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddTaskOptionFragment.REQUEST_CODE_ADD_TASK && resultCode == Activity.RESULT_OK) {
            newData()
        } else if (requestCode == PreviewTaskActivity.REQUEST_CODE_PREVIEW && resultCode == Activity.RESULT_OK) {
            val position = data?.getIntExtra(PreviewTaskActivity.EXTER_DATA_POSITION, -1)
            if (position != null && position != -1) {
                mTaskListFragment.deletePosition(position)
            } else {
                newData()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(localBroadcaseReceiver);
    }
}
