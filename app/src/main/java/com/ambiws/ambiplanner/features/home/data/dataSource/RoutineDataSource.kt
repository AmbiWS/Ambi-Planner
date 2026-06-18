package com.ambiws.ambiplanner.features.home.data.dataSource

import com.ambiws.ambiplanner.core.database.AppDatabase
import com.ambiws.ambiplanner.features.home.data.dataSource.local.model.RoutineEntity
import javax.inject.Inject

interface RoutineDataSource {
    suspend fun getAllRoutines(): List<RoutineEntity>
    suspend fun getRoutine(routineId: Int): RoutineEntity
    suspend fun insertAll(vararg routines: RoutineEntity)
    suspend fun delete(routine: RoutineEntity)
}

class RoutineDataSourceImpl @Inject constructor(database: AppDatabase) : RoutineDataSource {

    override suspend fun getAllRoutines(): List<RoutineEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoutine(routineId: Int): RoutineEntity {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(vararg routines: RoutineEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(routine: RoutineEntity) {
        TODO("Not yet implemented")
    }
}
