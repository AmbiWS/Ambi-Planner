package com.ambiws.ambiplanner.features.home.di

import com.ambiws.ambiplanner.features.home.data.dataSource.RoutineDataSource
import com.ambiws.ambiplanner.features.home.data.dataSource.RoutineDataSourceImpl
import com.ambiws.ambiplanner.features.home.data.dataSource.local.dao.RoutineDao
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoutineModule {

    @Provides
    @Singleton
    fun provideRoutineDataSource(routineDao: RoutineDao): RoutineDataSource {
        return RoutineDataSourceImpl(routineDao)
    }

    @Provides
    fun provideRoutineInteractor(dataSource: RoutineDataSource): RoutineInteractor {
        return RoutineInteractorImpl(dataSource)
    }
}
