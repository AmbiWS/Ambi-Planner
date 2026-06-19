package com.ambiws.ambiplanner.core.di.components

import com.ambiws.ambiplanner.core.di.factory.ViewModelFactory
import com.ambiws.ambiplanner.core.di.modules.DatabaseModule
import com.ambiws.ambiplanner.core.di.modules.NetModule
import com.ambiws.ambiplanner.core.di.modules.UtilsModule
import com.ambiws.ambiplanner.core.di.modules.ViewModelModule
import com.ambiws.ambiplanner.core.di.scopes.FeatureScope
import com.ambiws.ambiplanner.features.home.di.RoutineModule
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class, NetModule::class, UtilsModule::class, DatabaseModule::class, RoutineModule::class])
@FeatureScope
interface ViewModelComponent {

    val factory: ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelComponent
    }
}
