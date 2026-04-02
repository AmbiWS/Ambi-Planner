package com.ambiws.ambiplanner.features.dashboard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.features.dashboard.ui.navbar.BottomItem
import com.ambiws.ambiplanner.utils.SingleLiveEvent
import com.ambiws.ambiplanner.utils.extensions.mutable
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class DashboardViewModel @Inject constructor() : BaseViewModel() {

    lateinit var currentBottomNavController: LiveData<NavController>

    lateinit var isStartDestination: LiveData<Boolean>

    val bottomBarVisible: LiveData<Boolean> = MutableLiveData(true)

    val selectedTab = SingleLiveEvent<BottomItem>()

    init {
        launch {
            currentDestination.collect {
                bottomBarVisible.mutable().value =
                    when (it?.id) {
                        R.id.settingsFragment -> false
                        else -> true
                    }
            }
        }
    }

    fun connectBottomNavController(navControllerFlow: StateFlow<NavController>) {
        currentBottomNavController = navControllerFlow.asLiveData()
        isStartDestination =
            combine(currentDestination, navControllerFlow) { navDestination, navController ->
                navController.graph.startDestinationId == navDestination?.id
            }.asLiveData()
    }
}
