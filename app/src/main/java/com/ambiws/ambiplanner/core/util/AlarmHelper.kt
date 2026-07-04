package com.ambiws.ambiplanner.core.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.ambiws.ambiplanner.core.receiver.AlarmReceiver
import com.ambiws.ambiplanner.features.home.domain.model.Routine
import java.util.Calendar

class AlarmHelper(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    fun scheduleAlarm(routine: Routine) {
        if (!routine.isNotificationEnabled || routine.startTime == null) return

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_TITLE", routine.title)
            putExtra("EXTRA_DESCRIPTION", routine.description ?: "Your routine timer has ended.")
            putExtra("EXTRA_ID", routine.id ?: 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            routine.id ?: 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = calculateTriggerTime(routine.startTime, routine.timeToComplete)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager?.canScheduleExactAlarms() == true) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            } else {
                alarmManager?.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } else {
            alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    fun cancelAlarm(routineId: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            routineId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.cancel(pendingIntent)
    }

    private fun calculateTriggerTime(startTime: String, timeToComplete: String?): Long {
        val calendar = Calendar.getInstance()
        val parts = startTime.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
        calendar.set(Calendar.MINUTE, parts[1].toInt())
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        timeToComplete?.let {
            val h = it.substringBefore("h").trim().toIntOrNull() ?: 0
            val m = it.substringAfter("h").substringBefore("m").trim().toIntOrNull() ?: 0
            calendar.add(Calendar.HOUR_OF_DAY, h)
            calendar.add(Calendar.MINUTE, m)
        }

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }

        return calendar.timeInMillis
    }
}
