package com.ambiws.ambiplanner.features.home.data.dataSource

import com.ambiws.ambiplanner.features.home.data.dataSource.local.dao.RoutineDao
import com.ambiws.ambiplanner.features.home.data.dataSource.local.model.RoutineEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface RoutineDataSource {
    fun getAllRoutines(): Flow<List<RoutineEntity>>
    suspend fun getRoutine(routineId: Int): RoutineEntity
    suspend fun insertAll(vararg routines: RoutineEntity)
    suspend fun delete(routine: RoutineEntity)
}

class RoutineDataSourceImpl @Inject constructor(val routineDao: RoutineDao) : RoutineDataSource {

    override fun getAllRoutines(): Flow<List<RoutineEntity>> {
        return routineDao.getAllRoutines()
    }

    override suspend fun getRoutine(routineId: Int): RoutineEntity {
        return routineDao.getRoutine(routineId)
    }

    override suspend fun insertAll(vararg routines: RoutineEntity) {
        routineDao.insertAll(*routines)
    }

    override suspend fun delete(routine: RoutineEntity) {
        routineDao.delete(routine)
    }
}
