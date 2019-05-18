package me.smbduknow.vegandrinks.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, crossinline block: (T) -> Unit) {
    this.observe(owner, Observer<T> { value ->
        value?.let(block)
    })
}