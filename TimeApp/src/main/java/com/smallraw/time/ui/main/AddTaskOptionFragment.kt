package com.smallraw.time.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallraw.time.R
import com.smallraw.time.base.BaseFragment
import com.smallraw.time.ui.addtask.AddTaskActivity
import kotlinx.android.synthetic.main.fragment_add_task_option.*

class AddTaskOptionFragment : BaseFragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.fragment_add_task_option, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_reciprocal.setOnClickListener(this)
        layout_accumulative.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.layout_reciprocal -> {
                AddTaskActivity.startReciprocal(this.activity!!)
            }
            R.id.layout_accumulative -> {
                AddTaskActivity.startReciprocal(this.activity!!)
            }
            else -> {

            }
        }
    }
}