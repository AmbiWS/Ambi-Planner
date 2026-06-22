package com.ambiws.ambiplanner.features.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.features.home.mapper.toItemModel
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(val routineInteractor: RoutineInteractor) : BaseViewModel() {

    private val _routineLiveData = MutableLiveData<List<RoutineItemModel>>()
    val routineLiveData: LiveData<List<RoutineItemModel>> = _routineLiveData

    init {
        initRoutine()
    }

    fun navigateToEditRoutine(routine: RoutineItemModel?) {
        navigation.navigate(
            HomeFragmentDirections.actionHomeFragmentToEditRoutineItemFragment(routine)
        )
    }

    private fun initRoutine() {
        launch {
            val routines = withContext(ioContext) {
                routineInteractor.getAllRoutines().map {
                    it.toItemModel()
                }
            }
            _routineLiveData.value = routines
        }
    }
}
