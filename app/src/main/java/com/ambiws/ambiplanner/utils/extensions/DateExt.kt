package com.ambiws.ambiplanner.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedString(pattern: String = "yyyy-MM-dd"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}
