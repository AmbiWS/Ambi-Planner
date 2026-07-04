package com.ambiws.ambiplanner.features.home.ui.routine

import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.core.util.AlarmHelper
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.features.home.mapper.toRoutine
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import com.ambiws.ambiplanner.features.home.ui.routine.model.RoutineViewData
import javax.inject.Inject

class EditRoutineItemViewModel @Inject constructor(
    private val routineInteractor: RoutineInteractor,
    private val alarmHelper: AlarmHelper
) : BaseViewModel() {

    fun saveRoutine(routine: RoutineViewData) {
        launch(ioContext) {
            val domainRoutine = routine.toRoutine()
            val id = routineInteractor.insertAll(domainRoutine).getOrNull(0)?.toInt()
            
            val routineToSchedule = if (domainRoutine.id == null || domainRoutine.id == 0) {
                domainRoutine.copy(id = id)
            } else {
                domainRoutine
            }

            if (routineToSchedule.isNotificationEnabled) {
                alarmHelper.scheduleAlarm(routineToSchedule)
            } else {
                routineToSchedule.id?.let { alarmHelper.cancelAlarm(it) }
            }
        }
        navigation.navigateBack()
    }

    fun deleteRoutine(routine: RoutineItemModel) {
        launch(ioContext) {
            val domainRoutine = routine.toRoutine()
            routineInteractor.delete(domainRoutine)
            domainRoutine.id?.let { alarmHelper.cancelAlarm(it) }
        }
        navigation.navigateBack()
    }
}
