package com.smallraw.time.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallraw.time.R
import com.smallraw.time.base.BaseFragment
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.adapter.MemorialAdapter
import com.smallraw.time.ui.adapter.OnItemLongClickListener
import kotlinx.android.synthetic.main.fragment_task_list.*
import java.util.*

class TaskListFragment : BaseFragment() {
    private val mMemorialList = ArrayList<MemorialEntity>()
    private val mMemorialAdapter = MemorialAdapter(R.layout.main_item_memorial, mMemorialList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.fragment_task_list, container, false)
        initData()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setAdapterItemListener()
    }

    private fun setAdapterItemListener() {
        mMemorialAdapter.onItemLongClickListener = object : OnItemLongClickListener {
            override fun onLongClick(position: Int, holder: RecyclerView.ViewHolder): Boolean {
                val intent = Intent(this@TaskListFragment.context, PreviewTaskActivity::class.java)
                startActivity(intent)
                return true
            }
        }
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = mMemorialAdapter
    }

    private fun initData() {
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "139EED", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "9092A5", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "139EED", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 1, "9092A5", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", 0, "FFC529", Date(), Date(), Date()))
    }
}