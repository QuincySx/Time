package com.smallraw.time.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallraw.time.R
import com.smallraw.time.base.BaseFragment

class AddTaskFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.fragment_add_task, container, false)
        return mView
    }
}