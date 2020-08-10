package com.numeron.stateful.livedata

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.numeron.common.State

class StatefulLiveData<T> @JvmOverloads constructor(
        private val loading: String = "正在加载",
        private val failure: String = "加载失败"
) : MediatorLiveData<Stateful<T>>() {

    private val impl: StatefulImpl<T>
        get() = getValue() as? StatefulImpl ?: StatefulImpl(State.Empty)

    val value: T?
        @JvmName("value")
        get() = (getValue() as? StatefulImpl)?.value

    val requireValue: T
        @JvmName("requireValue")
        get() = value!!

    constructor(value: T) : this() {
        setValue(StatefulImpl(State.Success, value))
    }

    fun postLoading(progress: Float) {
        postLoading(this.loading, progress)
    }

    @JvmOverloads
    fun postLoading(message: String = this.loading, progress: Float = -1f) {
        postValue(impl.copy(state = State.Loading, progress = progress, message = message))
    }

    fun postSuccess(value: T) {
        postValue(impl.copy(state = State.Success, value = value))
    }

    fun postFailure(cause: Throwable, message: String = this.failure) {
        postValue(impl.copy(state = State.Failure, failure = cause, message = message))
    }

    fun postFailure(cause: Throwable) {
        postFailure(cause, failure)
    }

    fun postMessage(message: String) {
        postValue(impl.copy(message = message, version = impl.version + 1))
    }

    fun postEmpty(message: String) {
        postValue(impl.copy(state = State.Empty, message = message))
    }

    @Synchronized
    //使用同步锁，保证快速调用的顺序也是一致的
    override fun postValue(value: Stateful<T>) {
        if (isMainThread) {
            super.setValue(value)
        } else {
            super.postValue(value)
        }
    }

    companion object {

        fun <T> LiveData<T>.toStateful(loading: String = "正在加载",
                                       failure: String = "加载失败"): StatefulLiveData<T> {
            val statefulLiveData = StatefulLiveData<T>(loading, failure)
            statefulLiveData.addSource(this, statefulLiveData::postSuccess)
            return statefulLiveData
        }

        private val isMainThread: Boolean
            get() = Looper.myLooper() == Looper.getMainLooper()

    }

}