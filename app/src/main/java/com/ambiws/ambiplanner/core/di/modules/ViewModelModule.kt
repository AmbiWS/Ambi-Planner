package com.ambiws.ambiplanner.core.di.modules

import com.ambiws.ambiplanner.base.BaseViewModel
import com.ambiws.ambiplanner.core.di.vm.ViewModelKey
import com.ambiws.ambiplanner.features.home.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun provideHomeViewModel(homeViewModel: HomeViewModel): BaseViewModel
}
