package com.ambiws.ambiplanner.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ambiws.ambiplanner.App
import com.ambiws.ambiplanner.features.home.domain.model.DailySuccess
import com.ambiws.ambiplanner.utils.Const
import com.ambiws.ambiplanner.utils.extensions.addDays
import com.ambiws.ambiplanner.utils.extensions.toDate
import com.ambiws.ambiplanner.utils.extensions.toFormattedString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

class MidnightReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        val appComponent = (context.applicationContext as App).getAppComponent()
        val routineInteractor = appComponent.routineInteractor()
        val alarmHelper = appComponent.alarmHelper()
        val preferencesProvider = appComponent.preferencesProvider()

        val today = Date().toFormattedString()
        val lastResetDate = preferencesProvider.getString(Const.LAST_RESET_DATE_KEY)

        // Reschedule alarms if the day changed OR if the device rebooted (alarms are lost on boot)
        // Also check if this is the scheduled midnight alarm (intent.action is null)
        val isNewDay = lastResetDate != today
        val isReboot = intent.action == Intent.ACTION_BOOT_COMPLETED || 
                       intent.action == Intent.ACTION_TIME_CHANGED ||
                       intent.action == Intent.ACTION_TIMEZONE_CHANGED
        val isMidnightAlarm = intent.action == null

        if (isNewDay || isReboot || isMidnightAlarm) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (isNewDay && lastResetDate != null) {
                        val allRoutines = routineInteractor.getAllRoutines().first()
                        
                        // 1. Calculate and save success for the last active day
                        val lastDaySuccess = calculateSuccess(allRoutines)
                        preferencesProvider.saveDailySuccess(lastResetDate, lastDaySuccess)

                        // 2. Fill gaps if any (days where the app wasn't opened)
                        var gapDate = lastResetDate.toDate()?.addDays(1)
                        val todayDate = today.toDate()
                        
                        if (gapDate != null && todayDate != null) {
                            while (gapDate!!.before(todayDate)) {
                                preferencesProvider.saveDailySuccess(
                                    gapDate!!.toFormattedString(), 
                                    DailySuccess.NONE_DONE
                                )
                                gapDate = gapDate!!.addDays(1)
                            }
                        }

                        // 3. Reset all routines for the new day
                        routineInteractor.resetAllRoutines()
                        preferencesProvider.saveString(Const.LAST_RESET_DATE_KEY, today)
                    } else if (isNewDay && lastResetDate == null) {
                        // First time run
                        preferencesProvider.saveString(Const.LAST_RESET_DATE_KEY, today)
                    }
                    
                    // 4. Reschedule all alarms (Required on boot or new day)
                    val allRoutines = routineInteractor.getAllRoutines().first()
                    allRoutines.forEach { routine ->
                        if (routine.isNotificationEnabled && routine.startTime != null && !routine.isDone) {
                            alarmHelper.scheduleAlarm(routine)
                        }
                    }

                    // 5. Always reschedule the next midnight reset alarm
                    alarmHelper.scheduleMidnightReset()
                } finally {
                    pendingResult.finish()
                }
            }
        } else {
            pendingResult.finish()
        }
    }

    private fun calculateSuccess(routines: List<com.ambiws.ambiplanner.features.home.domain.model.Routine>): DailySuccess {
        if (routines.isEmpty()) return DailySuccess.NONE_DONE
        return when {
            routines.all { it.isDone } -> DailySuccess.ALL_DONE
            routines.any { it.isDone } -> DailySuccess.SOME_DONE
            else -> DailySuccess.NONE_DONE
        }
    }
}
