package com.ambiws.ambiplanner.features.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.features.home.mapper.toItemModel
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(val routineInteractor: RoutineInteractor) : BaseViewModel() {

    private val _routineLiveData = MutableLiveData<List<RoutineItemModel>>()
    val routineLiveData: LiveData<List<RoutineItemModel>> = _routineLiveData

    init {
        initRoutineList()
    }

    fun navigateToEditRoutine(routine: RoutineItemModel?) {
        navigation.navigate(
            HomeFragmentDirections.actionHomeFragmentToEditRoutineItemFragment(routine)
        )
    }

    private fun initRoutineList() {
        launch {
            routineInteractor.getAllRoutines().collect { routines ->
                _routineLiveData.postValue(routines.map { it.toItemModel() })
            }
        }
    }
}
