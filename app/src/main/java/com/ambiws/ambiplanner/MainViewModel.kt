package com.ambiws.ambiplanner

import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.utils.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val routineInteractor: RoutineInteractor
) : BaseViewModel() {

    val startDestinationEvent = SingleLiveEvent<Int>()

    fun initStartDestination() {
        startDestinationEvent.value = R.id.dashboardFragment
    }

    fun markRoutineAsDone(routineId: Int) {
        launch {
            routineInteractor.updateRoutineDoneStatus(routineId, true)
        }
    }
}
