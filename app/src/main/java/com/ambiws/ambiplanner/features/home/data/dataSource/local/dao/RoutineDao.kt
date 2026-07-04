package com.ambiws.ambiplanner.features.home.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ambiws.ambiplanner.features.home.data.dataSource.local.model.RoutineEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface RoutineDao {

    @Query("SELECT * FROM RoutineEntity ORDER BY is_done ASC")
    fun getAllRoutines(): Flow<List<RoutineEntity>>

    @Query("SELECT * FROM RoutineEntity WHERE id = (:routineId)")
    suspend fun getRoutine(routineId: Int): RoutineEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg routines: RoutineEntity): List<Long>

    @Delete
    suspend fun delete(routine: RoutineEntity)
}
