package com.smallraw.time.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.smallraw.time.R
import com.smallraw.time.base.BaseActivity
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.ui.adapter.MemorialAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {
    private val mMemorialList = ArrayList<MemorialEntity>()
    private val mMemorialAdapter = MemorialAdapter(R.layout.main_item_memorial, mMemorialList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mMemorialAdapter
    }

    private fun initData() {
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 1, "139EED", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "FFC529", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 1, "9092A5", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "FFC529", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 1, "139EED", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "FFC529", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "EE386D", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 1, "9092A5", Date(), Date(), Date()))
        mMemorialList.add(MemorialEntity("纪念日", "什么都不纪念", Date(), 0, "FFC529", Date(), Date(), Date()))
    }
}
