package com.smallraw.time.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManger: FragmentManager) : FragmentPagerAdapter(fragmentManger) {

    val fragmentArrayList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList[position]
    }

    override fun getCount() = fragmentArrayList.size

    fun addFragment(fragment: Fragment) {
        fragmentArrayList.add(fragment)
    }
}