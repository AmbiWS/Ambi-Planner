package com.ambiws.ambiplanner

import androidx.lifecycle.ViewModel
import com.ambiws.ambiplanner.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    val startDestinationEvent = SingleLiveEvent<Int>()

    fun initStartDestination() {
        startDestinationEvent.value = R.id.homeFragment
    }
}
