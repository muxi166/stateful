package com.numeron.stateful.livedata

import androidx.lifecycle.Observer
import com.numeron.common.State

class StatefulObserver<T>(private val callback: StatefulCallback<T>) : Observer<Stateful<T>> {

    private var previousState: State? = null

    override fun onChanged(stateful: Stateful<T>?) {
        if (stateful != null && stateful.state != previousState) {
            callback.onStateChanged(stateful.state)
            previousState = stateful.state
        }
        stateful?.onFailure(callback::onFailure)
                ?.onLoading(callback::onLoading)
                ?.onSuccess(callback::onSuccess)
                ?.onEmpty(callback::onEmpty)
                ?.onMessage(callback::onMessage)
    }

}
