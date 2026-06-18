package com.ambiws.ambiplanner.features.home.mapper

import com.ambiws.ambiplanner.features.home.data.dataSource.local.model.RoutineEntity
import com.ambiws.ambiplanner.features.home.domain.model.Routine
import com.ambiws.ambiplanner.features.home.ui.routine.model.RoutineViewData

fun RoutineEntity.toRoutine() = Routine(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
)

fun Routine.toViewData() = RoutineViewData(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
)

fun RoutineViewData.toRoutine() = Routine(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
)
