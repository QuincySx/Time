package com.smallraw.time.base

import android.os.Handler
import android.os.Message
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment : Fragment() {
    companion object {
        private val HANDLER_MSG_PROMPT = 1
    }

    protected lateinit var mView: View
    protected val mHandler = Handler()

    fun showPrompt(@StringRes res: Int) {
        showPrompt(getString(res))
    }

    fun showPrompt(msg: String) {
        val msg = Message.obtain()
        msg.what = BaseFragment.HANDLER_MSG_PROMPT
        msg.obj = msg
        mHandler.sendMessage(msg)
    }
}