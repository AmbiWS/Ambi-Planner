package com.ambiws.ambiplanner.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ambiws.ambiplanner.features.home.data.dataSource.local.dao.RoutineDao
import com.ambiws.ambiplanner.features.home.data.dataSource.local.model.RoutineEntity

@Database(entities = [RoutineEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
}
