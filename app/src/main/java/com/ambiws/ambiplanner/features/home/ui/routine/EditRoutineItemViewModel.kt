package com.ambiws.ambiplanner.features.home.ui.routine

import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.features.home.mapper.toRoutine
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import com.ambiws.ambiplanner.features.home.ui.routine.model.RoutineViewData
import javax.inject.Inject

class EditRoutineItemViewModel @Inject constructor(val routineInteractor: RoutineInteractor) : BaseViewModel() {

    fun saveRoutine(routine: RoutineViewData) {
        launch(ioContext) {
            routineInteractor.insertAll(routine.toRoutine())
        }
        navigation.navigateBack()
    }

    fun deleteRoutine(routine: RoutineItemModel) {
        launch(ioContext) {
            routineInteractor.delete(routine.toRoutine())
        }
        navigation.navigateBack()
    }
}
