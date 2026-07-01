package com.ambiws.ambiplanner.features.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.features.home.mapper.toItemModel
import com.ambiws.ambiplanner.features.home.mapper.toRoutine
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import java.util.Calendar
import javax.inject.Inject

class HomeViewModel @Inject constructor(val routineInteractor: RoutineInteractor) : BaseViewModel() {

    private val _routineLiveData = MutableLiveData<List<RoutineItemModel>>()
    val routineLiveData: LiveData<List<RoutineItemModel>> = _routineLiveData

    init {
        initRoutineList()
    }

    fun onRoutineDoneChanged(routine: RoutineItemModel, isDone: Boolean) {
        launch {
            routineInteractor.insertAll(routine.copy(isDone = isDone).toRoutine())
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
                _routineLiveData.postValue(sortRoutinesList(items))
            }
        }
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

        val durationMillis = parseDuration(timeToComplete)
        val endMillis = startCal.timeInMillis + durationMillis

        return when {
            now >= endMillis -> -1L
            now >= startCal.timeInMillis -> endMillis - now
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
