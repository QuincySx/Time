package com.smallraw.time.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.smallraw.time.R
import com.smallraw.time.base.BaseActivity
import com.smallraw.time.ui.adapter.TaskAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val mTaskList = ArrayList<String>()
    private val mTaskAdapter = TaskAdapter(R.layout.main_item_task, mTaskList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mTaskAdapter
    }

    private fun initData() {

    }
}
