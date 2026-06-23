package com.ambiws.ambiplanner.features.home.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ambiws.ambiplanner.features.home.data.dataSource.local.model.RoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Query("SELECT * FROM RoutineEntity")
    fun getAllRoutines(): Flow<List<RoutineEntity>>

    @Query("SELECT * FROM RoutineEntity WHERE id = (:routineId)")
    suspend fun getRoutine(routineId: Int): RoutineEntity

    @Insert
    suspend fun insertAll(vararg routines: RoutineEntity)

    @Delete
    suspend fun delete(routine: RoutineEntity)
}
