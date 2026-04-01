package com.ambiws.ambiplanner.utils

import android.util.Log
import com.ambiws.ambiplanner.BuildConfig
import com.ambiws.ambiplanner.utils.extensions.className

private val loggingEnabled = BuildConfig.DEBUG

fun Any.loge(msg: String?) {
    if (loggingEnabled) {
        Log.e("e".plus(TAG), msg ?: "null")
    }
}

fun Any.loge(msg: String?, throwable: Throwable?) {
    if (loggingEnabled) {
        Log.e("e".plus(TAG), msg ?: "null", throwable ?: Throwable())
    }
}

fun Any.logd(msg: String?) {
    if (loggingEnabled) {
        Log.d("d".plus(TAG), msg ?: "null")
    }
}

fun Any.logw(msg: String?) {
    if (loggingEnabled) {
        Log.w("w".plus(TAG), msg ?: "null")
    }
}

fun Any.logi(msg: String?) {
    if (loggingEnabled) {
        Log.i("i".plus(TAG), msg ?: "null")
    }
}

fun Any.logv(msg: String?) {
    if (loggingEnabled) {
        Log.v("v".plus(TAG), msg ?: "null")
    }
}

fun Any.logwtf(msg: String?) {
    if (loggingEnabled) {
        Log.wtf("f".plus(TAG), msg ?: "null")
    }
}

private val <T : Any> T.TAG: String
    get() = fixTagLength("${Const.APP_NAME_SHORT}_$className")

private fun fixTagLength(tag: String): String {
    return if (tag.length <= Const.MAX_LOGGING_TAG_LENGTH) {
        tag
    } else {
        tag.substring(0, Const.MAX_LOGGING_TAG_LENGTH - 1)
    }
}
