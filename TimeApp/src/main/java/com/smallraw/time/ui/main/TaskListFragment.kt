package com.smallraw.time.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallraw.time.R
import com.smallraw.time.base.BaseFragment
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.adapter.MemorialAdapter
import com.smallraw.time.ui.adapter.OnItemClickListener
import com.smallraw.time.ui.adapter.OnItemLongClickListener
import kotlinx.android.synthetic.main.fragment_task_list.*
import java.util.*

class TaskListFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance(dataCallback: DataRepositoryCallback): TaskListFragment {
            return newInstance(null, null, dataCallback)
        }

        @JvmStatic
        fun newInstance(clickListener: OnItemClickListener, dataCallback: DataRepositoryCallback): TaskListFragment {
            return newInstance(clickListener, null, dataCallback)
        }

        @JvmStatic
        fun newInstance(longClickListener: OnItemLongClickListener, dataCallback: DataRepositoryCallback): TaskListFragment {
            return newInstance(null, longClickListener, dataCallback)
        }

        @JvmStatic
        fun newInstance(clickListener: OnItemClickListener?, longClickListener: OnItemLongClickListener?, dataCallback: DataRepositoryCallback): TaskListFragment {
            val taskListFragment = TaskListFragment()
            taskListFragment.mClickListener = clickListener
            taskListFragment.mLongClickListener = longClickListener
            taskListFragment.mDataCallback = dataCallback
            return taskListFragment
        }
    }

    private val mMemorialList = ArrayList<MemorialEntity>()
    private val mMemorialAdapter = MemorialAdapter(R.layout.main_item_memorial, mMemorialList)
    private var mLongClickListener: OnItemLongClickListener? = null
    private var mClickListener: OnItemClickListener? = null
    private var mDataCallback: DataRepositoryCallback? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.fragment_task_list, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setAdapterItemListener()
        mDataCallback?.getData()
    }

    private fun setAdapterItemListener() {
        if (mLongClickListener != null) {
            mMemorialAdapter.onItemLongClickListener = mLongClickListener
        }
        if (mClickListener != null) {
            mMemorialAdapter.onItemClickListener = mClickListener
        }
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = mMemorialAdapter
    }

    fun getDate(): List<MemorialEntity> {
        return mMemorialList
    }

    fun newDate(data: List<MemorialEntity>) {
        mMemorialList.clear()
        mMemorialList.addAll(data)
        mMemorialAdapter.notifyDataSetChanged()
    }

    fun addDate(data: List<MemorialEntity>) {
        mMemorialList.addAll(data)
    }

    fun addDate(data: MemorialEntity) {
        mMemorialList.add(data)
    }

    fun notifyDataSetChanged() {
        mMemorialAdapter.notifyDataSetChanged()
    }

    public interface DataRepositoryCallback {
        fun getData()
    }
}