package com.ambiws.ambiplanner.features.home.data.dataSource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "start_time") val startTime: String?,
    @ColumnInfo(name = "time_to_complete") val timeToComplete: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false,
)
