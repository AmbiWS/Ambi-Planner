package com.ambiws.ambiplanner.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedString(pattern: String = "yyyy-MM-dd"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun String.toDate(pattern: String = "yyyy-MM-dd"): Date? {
    return try {
        SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.addDays(days: Int): Date {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.add(java.util.Calendar.DATE, days)
    return calendar.time
}
