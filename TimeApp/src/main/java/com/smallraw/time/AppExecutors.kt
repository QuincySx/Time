package com.smallraw.time

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


public class AppExecutors {
    private val mDiskIO: Executor;

    private val mNetworkIO: Executor;

    private val mMainThread: Executor;

    constructor(diskIO: Executor, networkIO: Executor, mainThread: Executor) {
        this.mDiskIO = diskIO
        this.mNetworkIO = networkIO
        this.mMainThread = mainThread
    }

    constructor () : this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()),
            MainThreadExecutor())

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}

