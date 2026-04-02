package com.ambiws.ambiplanner.utils.extensions

import android.content.res.Resources


val Int.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()

val Float.dp: Float get() = (Resources.getSystem().displayMetrics.density * this)
