package com.android.sunshineapp

import android.os.Handler
import android.os.Looper
import android.support.annotation.MainThread
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutor(private val diskIO:Executor,private val networkIO:Executor,private val mainThread: Executor) {
    //singleton
    companion object {
        private var aInsstance:AppExecutor?=null
        fun getInstanceExecutor():AppExecutor{
            if(aInsstance!=null) return aInsstance!!
            synchronized(this){
                aInsstance=
                        AppExecutor(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),MainThreadExecutor())
                return aInsstance!!
            }

        }
    }

    /**
     * for read db
     */
    fun diskIO():Executor{return diskIO}

    /**
     * for network
     */
    fun networkIO():Executor{return networkIO}

private class MainThreadExecutor:Executor{
    override fun execute(command: Runnable?) {
        mainThreadHandler.post(command)
    }
    private val mainThreadHandler=Handler(Looper.getMainLooper())

}
}