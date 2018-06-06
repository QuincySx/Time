package com.smallraw.time.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class RefreshMainDataReceiver extends BroadcastReceiver {
    public static final String BroadcastRefreshMain = "LocalBroadcaseReceiver";

    public static void sendBroadcast(Context context) {
        Intent intentBroadcast = new Intent();
        intentBroadcast.setAction(RefreshMainDataReceiver.BroadcastRefreshMain);
        context.sendBroadcast(intentBroadcast);
    }
}
