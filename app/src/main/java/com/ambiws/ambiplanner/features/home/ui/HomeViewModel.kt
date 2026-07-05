package com.ambiws.ambiplanner.features.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.core.util.AlarmHelper
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.features.home.mapper.toItemModel
import com.ambiws.ambiplanner.features.home.mapper.toRoutine
import com.ambiws.ambiplanner.features.home.domain.model.DailySuccess
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import com.ambiws.ambiplanner.utils.extensions.toFormattedString
import com.ambiws.ambiplanner.utils.providers.PreferencesProvider
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val routineInteractor: RoutineInteractor,
    private val alarmHelper: AlarmHelper,
    private val preferencesProvider: PreferencesProvider
) : BaseViewModel() {

    private val _routineLiveData = MutableLiveData<List<RoutineItemModel>>()
    val routineLiveData: LiveData<List<RoutineItemModel>> = _routineLiveData

    init {
        initRoutineList()
    }

    fun onRoutineDoneChanged(routine: RoutineItemModel, isDone: Boolean) {
        launch {
            val updatedRoutine = routine.copy(isDone = isDone)
            routineInteractor.insertAll(updatedRoutine.toRoutine())

            if (updatedRoutine.isNotificationEnabled) {
                if (isDone) {
                    alarmHelper.cancelAlarm(updatedRoutine.id)
                } else {
                    alarmHelper.scheduleAlarm(updatedRoutine.toRoutine())
                }
            }
        }
    }

    fun navigateToEditRoutine(routine: RoutineItemModel?) {
        navigation.navigate(
            HomeFragmentDirections.actionHomeFragmentToEditRoutineItemFragment(routine)
        )
    }

    private fun initRoutineList() {
        launch {
            routineInteractor.getAllRoutines().collect { routines ->
                val items = routines.map { it.toItemModel() }
                updateDailySuccess(items)
                _routineLiveData.postValue(sortRoutinesList(items))
            }
        }
    }

    private fun updateDailySuccess(routines: List<RoutineItemModel>) {
        if (routines.isEmpty()) return

        val success = when {
            routines.all { it.isDone } -> DailySuccess.ALL_DONE
            routines.any { it.isDone } -> DailySuccess.SOME_DONE
            else -> DailySuccess.NONE_DONE
        }
        preferencesProvider.saveDailySuccess(Date().toFormattedString(), success)
    }

    private fun sortRoutinesList(routines: List<RoutineItemModel>): List<RoutineItemModel> {
        val now = Calendar.getInstance().timeInMillis

        return routines.sortedWith(
            compareBy<RoutineItemModel> { it.isDone }
                .thenBy { it.startTime == null }
                .thenBy { item ->
                    if (item.startTime == null) 0L
                    else calculateTimeLeft(item.startTime, item.timeToComplete, now)
                }
        )
    }

    private fun calculateTimeLeft(startTime: String, timeToComplete: String?, now: Long): Long {
        val startCal = Calendar.getInstance().apply {
            val parts = startTime.split(":")
            if (parts.size == 2) {
                set(Calendar.HOUR_OF_DAY, parts[0].toInt())
                set(Calendar.MINUTE, parts[1].toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        return when {
            now >= startCal.timeInMillis -> -1L
            else -> startCal.timeInMillis - now
        }
    }

    private fun parseDuration(timeToComplete: String?): Long {
        if (timeToComplete == null) return 0
        val regex = "(\\d+)h (\\d+)m".toRegex()
        val matchResult = regex.find(timeToComplete)
        return if (matchResult != null) {
            val (h, m) = matchResult.destructured
            (h.toLong() * 3600 + m.toLong() * 60) * 1000
        } else 0
    }
}
