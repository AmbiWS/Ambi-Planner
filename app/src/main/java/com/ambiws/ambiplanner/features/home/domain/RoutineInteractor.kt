package com.ambiws.ambiplanner.features.home.domain

import com.ambiws.ambiplanner.features.home.data.dataSource.RoutineDataSource
import com.ambiws.ambiplanner.features.home.domain.model.Routine
import com.ambiws.ambiplanner.features.home.mapper.toRoutine
import com.ambiws.ambiplanner.features.home.mapper.toRoutineEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RoutineInteractor {
    fun getAllRoutines(): Flow<List<Routine>>
    suspend fun getRoutine(routineId: Int): Routine
    suspend fun insertAll(vararg routines: Routine): List<Long>
    suspend fun delete(routine: Routine)
}

class RoutineInteractorImpl @Inject constructor(val routineDataSource: RoutineDataSource) : RoutineInteractor {

    override fun getAllRoutines(): Flow<List<Routine>> {
        return routineDataSource.getAllRoutines().map {
            it.map { routineEntity -> routineEntity.toRoutine() }
        }
    }

    override suspend fun getRoutine(routineId: Int): Routine {
        return routineDataSource.getRoutine(routineId).toRoutine()
    }

    override suspend fun insertAll(vararg routines: Routine): List<Long> {
        return routineDataSource.insertAll(*routines.map { it.toRoutineEntity() }.toTypedArray())
    }

    override suspend fun delete(routine: Routine) {
        routineDataSource.delete(routine.toRoutineEntity())
    }
}
