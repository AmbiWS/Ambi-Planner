package com.ambiws.ambiplanner.features.home.ui

import com.ambiws.ambiplanner.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    fun navigateToEditRoutine() {
        navigation.navigate(
            HomeFragmentDirections.actionHomeFragmentToEditRoutineItemFragment()
        )
    }
}
