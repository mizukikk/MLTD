package com.mizukikk.mltd.room

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DBExecutor {

    private val _dbIOThread = DBIOExecutor()
    private val _mainThread = MainThreadExecutor()

    val dbIOThread get() = _dbIOThread
    val mainThread get() = _mainThread

    inner class DBIOExecutor : Executor {
        private val dbIOexecutor = Executors.newSingleThreadExecutor()
        override fun execute(command: Runnable) {
            dbIOexecutor.execute(command)
        }
    }

    inner class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }


}