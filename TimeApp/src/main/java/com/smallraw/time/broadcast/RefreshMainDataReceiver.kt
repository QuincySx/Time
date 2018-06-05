package com.smallraw.time.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

open class RefreshMainDataReceiver : BroadcastReceiver() {
    companion object {
        val BroadcastRefreshMain = "LocalBroadcaseReceiver"

        fun sendBroadcast(context: Context) {
            val intentBroadcast = Intent()
            intentBroadcast.setAction(RefreshMainDataReceiver.BroadcastRefreshMain)
            context.sendBroadcast(intentBroadcast)
        }
    }

    open override fun onReceive(context: Context?, intent: Intent?): Unit {
    }

}
