package com.ambiws.ambiplanner

import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.utils.SingleLiveEvent

class MainViewModel : BaseViewModel() {

    val startDestinationEvent = SingleLiveEvent<Int>()

    fun initStartDestination() {
        startDestinationEvent.value = R.id.dashboardFragment
    }
}
