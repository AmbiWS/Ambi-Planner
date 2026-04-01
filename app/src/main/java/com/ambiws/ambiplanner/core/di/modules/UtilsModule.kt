package com.ambiws.ambiplanner.core.di.modules

import android.content.Context
import com.ambiws.ambiplanner.utils.providers.PreferencesProvider
import com.ambiws.ambiplanner.utils.providers.PreferencesProviderImpl
import com.ambiws.ambiplanner.utils.providers.ResourceProvider
import com.ambiws.ambiplanner.utils.providers.ResourceProviderImpl
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }

    @Provides
    fun providePreferencesProvider(context: Context): PreferencesProvider {
        return PreferencesProviderImpl(context)
    }
}
