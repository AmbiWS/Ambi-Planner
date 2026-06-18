package com.ambiws.ambiplanner.core.di.modules

import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.core.di.vm.ViewModelKey
import com.ambiws.ambiplanner.features.calendar.ui.CalendarViewModel
import com.ambiws.ambiplanner.features.dashboard.ui.DashboardViewModel
import com.ambiws.ambiplanner.features.home.ui.routine.EditRoutineItemViewModel
import com.ambiws.ambiplanner.features.home.ui.HomeViewModel
import com.ambiws.ambiplanner.features.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun provideHomeViewModel(homeViewModel: HomeViewModel): BaseViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    fun provideDashboardViewModel(dashboardViewModel: DashboardViewModel): BaseViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    fun provideCalendarViewModel(calendarViewModel: CalendarViewModel): BaseViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun provideSettingsViewModel(settingsViewModel: SettingsViewModel): BaseViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditRoutineItemViewModel::class)
    fun provideEditRoutineItemViewModel(editRoutineItemViewModel: EditRoutineItemViewModel): BaseViewModel
}
