package com.ambiws.ambiplanner.core.di.components

import com.ambiws.ambiplanner.core.database.AppDatabase
import com.ambiws.ambiplanner.core.di.modules.AppModule
import com.ambiws.ambiplanner.core.di.modules.DatabaseModule
import com.ambiws.ambiplanner.core.di.modules.UtilsModule
import com.ambiws.ambiplanner.core.di.scopes.AppScope
import com.ambiws.ambiplanner.core.util.AlarmHelper
import com.ambiws.ambiplanner.features.home.data.dataSource.local.dao.RoutineDao
import com.ambiws.ambiplanner.features.home.di.RoutineModule
import com.ambiws.ambiplanner.features.home.domain.RoutineInteractor
import com.ambiws.ambiplanner.utils.providers.PreferencesProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        RoutineModule::class,
        UtilsModule::class
    ]
)
@Singleton
@AppScope
interface AppComponent {

    val viewModelComponent: ViewModelComponent.Builder

    fun appDatabase(): AppDatabase
    fun routineDao(): RoutineDao
    fun routineInteractor(): RoutineInteractor
    fun alarmHelper(): AlarmHelper
    fun preferencesProvider(): PreferencesProvider

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}
