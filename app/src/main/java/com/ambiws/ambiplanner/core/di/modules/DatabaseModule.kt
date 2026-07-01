package com.ambiws.ambiplanner.core.di.modules

import android.content.Context
import androidx.room.Room
import com.ambiws.ambiplanner.core.database.AppDatabase
import com.ambiws.ambiplanner.features.home.data.dataSource.local.dao.RoutineDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    private val databaseName = "ambiplannerdb"

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRoutineDao(database: AppDatabase): RoutineDao {
        return database.routineDao()
    }
}
