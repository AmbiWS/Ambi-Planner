package com.ambiws.ambiplanner.utils.extensions

val <T : Any> T.className: String
    get() = this::class.java.simpleName

fun <T : Any> T?.throwIfNull(): T {
    return this ?: throw NullPointerException("Value can't be null")
}
