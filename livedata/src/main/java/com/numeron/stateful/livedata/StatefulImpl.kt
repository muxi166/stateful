package com.numeron.stateful.livedata

import com.numeron.common.State

internal data class StatefulImpl<T>(
        override val state: State,
        internal val value: T? = null,
        internal val failure: Throwable? = null,
        internal val progress: Float = -1f,
        internal val message: String? = null,
        internal val version: Int = 0,
        internal var previous: Int = 0
) : Stateful<T> {

    override fun onSuccess(c: (T) -> Unit): Stateful<T> {
        if (state == State.Success && value != null) {
            c(value)
        }
        return this
    }

    override fun onFailure(c: (String, Throwable) -> Unit): Stateful<T> {
        if (state == State.Failure && failure != null) {
            c(message!!, failure)
        }
        return this
    }

    override fun onLoading(c: (String, Float) -> Unit): Stateful<T> {
        if (state == State.Loading) {
            c(message!!, progress)
        }
        return this
    }

    override fun onEmpty(c: (String) -> Unit): Stateful<T> {
        if (state == State.Empty) {
            c(message!!)
        }
        return this
    }

    override fun onMessage(c: (String) -> Unit): Stateful<T> {
        if (version > previous && message != null) {
            previous = version
            c(message)
        }
        return this
    }

}
