package com.ambiws.ambiplanner

import android.app.Application
import com.ambiws.ambiplanner.core.di.components.AppComponent
import com.ambiws.ambiplanner.core.di.components.DaggerAppComponent

class App : Application() {

    // TODO Add Pagination for educational purposes (paging3 and custom)
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}
