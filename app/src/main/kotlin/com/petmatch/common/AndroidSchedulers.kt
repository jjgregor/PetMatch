package com.petmatch.common

import com.petmatch.interfaces.Schedulers
import io.reactivex.Scheduler

object AndroidSchedulers : Schedulers {

    override fun mainThread(): Scheduler {
        return io.reactivex.android.schedulers.AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return io.reactivex.schedulers.Schedulers.io()
    }

    override fun computation(): Scheduler {
        return io.reactivex.schedulers.Schedulers.computation()
    }
}