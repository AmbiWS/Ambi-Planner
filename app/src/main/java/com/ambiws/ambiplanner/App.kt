package com.ambiws.ambiplanner

import android.app.Application
import com.ambiws.ambiplanner.core.di.components.AppComponent
import com.ambiws.ambiplanner.core.di.components.DaggerAppComponent
import com.ambiws.ambiplanner.core.di.modules.AppModule

class App : Application() {

    // TODO Add Pagination for educational purposes (paging3 and custom)
    // TODO Implement scopes using dagger 2
    // TODO Refactor code
    // TODO Optimize processes
    // TODO Implement custom listdiffer and itemmodel
    // TODO Add time calculation ext and refactor code
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}
