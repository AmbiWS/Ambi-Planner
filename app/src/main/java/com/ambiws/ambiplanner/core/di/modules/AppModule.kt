package com.ambiws.ambiplanner.core.di.modules

import android.app.Application
import android.content.Context
import com.ambiws.ambiplanner.core.util.AlarmHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideAlarmHelper(context: Context): AlarmHelper = AlarmHelper(context)
}
