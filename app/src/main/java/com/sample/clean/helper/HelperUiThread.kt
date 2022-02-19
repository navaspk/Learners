package com.sample.clean.helper

import com.sample.core.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class HelperUiThread @Inject constructor() : PostExecutionThread {
    override fun scheduler(): Scheduler = AndroidSchedulers.mainThread()
}