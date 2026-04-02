package com.ambiws.ambiplanner.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T> AppCompatActivity.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(this) {
        if (it != null) {
            onNext(it)
        }
    }
}

fun <T> Fragment.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(viewLifecycleOwner) {
        if (it != null) {
            onNext(it)
        }
    }
}

fun <T> Fragment.subscribeNullable(liveData: LiveData<T>?, onNext: (t: T?) -> Unit) {
    liveData ?: return
    liveData.observe(viewLifecycleOwner, Observer { onNext(it) })
}

fun <T> LiveData<T>.mutable(): MutableLiveData<T> =
    (this as? MutableLiveData) ?: throw IllegalArgumentException("LiveData is not mutable")

fun <T> StateFlow<T>.mutable(): MutableStateFlow<T> =
    (this as? MutableStateFlow) ?: throw IllegalArgumentException("StateFlow is not mutable")
