package com.smallraw.time.ui.recycleBin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smallraw.time.App
import com.smallraw.time.R
import com.smallraw.time.base.BaseTitleBarActivity
import com.smallraw.time.base.RudenessScreenHelper
import com.smallraw.time.model.thoroughDeleteTaskAll
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.adapter.OnItemClickListener
import com.smallraw.time.ui.archivingClip.ArchivingClipManagerActivity
import com.smallraw.time.ui.main.PreviewTaskActivity
import com.smallraw.time.ui.main.TaskListFragment

class RecycleBinActivity : BaseTitleBarActivity() {
    lateinit var mTaskListFragment: TaskListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_bin)
        initTitleRight()
        initFragment()
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
        right.setBackgroundResource(R.drawable.ic_smash_black)
        right.setOnClickListener {
            (application as App).getAppExecutors().diskIO().execute {
                thoroughDeleteTaskAll(application, mTaskListFragment.getDate())
                mTaskListFragment.clearDate()
                mTaskListFragment.notifyDataSetChanged()
            }
        }
        return right
    }

    private fun initFragment() {
        mTaskListFragment = TaskListFragment.newInstance(object : OnItemClickListener {
            override fun onClick(position: Int, holder: RecyclerView.ViewHolder, item: MemorialEntity) {
                RecycleBinManagerActivity.start(this@RecycleBinActivity, item, holder.adapterPosition, ArchivingClipManagerActivity.REQUEST_CODE_PREVIEW)
            }
        }, object : TaskListFragment.DataRepositoryCallback {
            override fun getData() {
                initData()
            }
        })
        supportFragmentManager.beginTransaction().add(R.id.root_layout, mTaskListFragment, null).commit()
    }

    private fun initData() {
        (application as App).getAppExecutors().networkIO().execute {
            val memorialList = (application as App).getRepository().getTaskStrike(true)
            mTaskListFragment.newDate(memorialList)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PreviewTaskActivity.REQUEST_CODE_PREVIEW && resultCode == Activity.RESULT_OK) {
            val position = data?.getIntExtra(RecycleBinManagerActivity.EXTER_DATA_POSITION, -1)
            if (position != null && position != -1) {
                mTaskListFragment.deletePosition(position)
            } else {
                initData()
            }
        }
    }
}
