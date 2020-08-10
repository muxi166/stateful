package com.numeron.stateful.livedata

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class StatefulExceptionHandler<T>(private val statefulLiveData: StatefulLiveData<T>) :
    CoroutineExceptionHandler {

    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        statefulLiveData.postFailure(exception)
        exception.printStackTrace()
    }

}