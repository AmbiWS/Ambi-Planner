package com.ambiws.ambiplanner.features.calendar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.features.home.domain.model.DailySuccess
import com.ambiws.ambiplanner.utils.providers.PreferencesProvider
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val preferencesProvider: PreferencesProvider
) : BaseViewModel() {

    private val _successStats = MutableLiveData<Map<DailySuccess, Int>>()
    val successStats: LiveData<Map<DailySuccess, Int>> = _successStats

    init {
        loadStats()
    }

    private fun loadStats() {
        _successStats.value = preferencesProvider.getDailySuccessStats()
    }
}
