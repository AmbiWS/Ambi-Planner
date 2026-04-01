package com.ambiws.ambiplanner.core.di.components

import android.content.Context
import com.ambiws.ambiplanner.core.di.factory.ViewModelFactory
import com.ambiws.ambiplanner.core.di.modules.NetModule
import com.ambiws.ambiplanner.core.di.modules.UtilsModule
import com.ambiws.ambiplanner.core.di.modules.ViewModelModule
import com.ambiws.ambiplanner.core.di.scopes.FeatureScope
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class, NetModule::class, UtilsModule::class])
@FeatureScope
interface ViewModelComponent {

    val factory: ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): ViewModelComponent
    }
}
