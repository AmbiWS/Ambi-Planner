package com.ambiws.ambiplanner.features.home.domain

import com.ambiws.ambiplanner.features.home.data.dataSource.RoutineDataSource
import com.ambiws.ambiplanner.features.home.domain.model.Routine
import com.ambiws.ambiplanner.features.home.mapper.toRoutine
import com.ambiws.ambiplanner.features.home.mapper.toRoutineEntity
import javax.inject.Inject


interface RoutineInteractor {
    suspend fun getAllRoutines(): List<Routine>
    suspend fun getRoutine(routineId: Int): Routine
    suspend fun insertAll(vararg routines: Routine)
    suspend fun delete(routine: Routine)
}

class RoutineInteractorImpl @Inject constructor(val routineDataSource: RoutineDataSource) : RoutineInteractor {

    override suspend fun getAllRoutines(): List<Routine> {
        return routineDataSource.getAllRoutines().map { 
            it.toRoutine()
        }
    }

    override suspend fun getRoutine(routineId: Int): Routine {
        return routineDataSource.getRoutine(routineId).toRoutine()
    }

    override suspend fun insertAll(vararg routines: Routine) {
        routineDataSource.insertAll(*routines.map { it.toRoutineEntity() }.toTypedArray())
    }

    override suspend fun delete(routine: Routine) {
        routineDataSource.delete(routine.toRoutineEntity())
    }
}
