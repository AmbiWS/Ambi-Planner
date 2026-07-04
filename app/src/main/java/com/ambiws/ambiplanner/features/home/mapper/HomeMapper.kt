package com.ambiws.ambiplanner.features.home.mapper

import com.ambiws.ambiplanner.features.home.data.dataSource.local.model.RoutineEntity
import com.ambiws.ambiplanner.features.home.domain.model.Routine
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import com.ambiws.ambiplanner.features.home.ui.routine.model.RoutineViewData
import com.ambiws.ambiplanner.utils.extensions.throwIfNull

fun RoutineEntity.toRoutine() = Routine(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
    isDone = isDone,
    isNotificationEnabled = isNotificationEnabled,
)

fun Routine.toViewData() = RoutineViewData(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
    isDone = isDone,
    isNotificationEnabled = isNotificationEnabled,
)

fun RoutineViewData.toRoutine() = Routine(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
    isDone = isDone,
    isNotificationEnabled = isNotificationEnabled,
)

fun Routine.toRoutineEntity() = RoutineEntity(
    id = id ?: 0,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
    isDone = isDone,
    isNotificationEnabled = isNotificationEnabled,
)

fun Routine.toItemModel() = RoutineItemModel(
    id = id.throwIfNull(),
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
    isDone = isDone,
    isNotificationEnabled = isNotificationEnabled,
)

fun RoutineItemModel.toRoutine() = Routine(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    timeToComplete = timeToComplete,
    isDone = isDone,
    isNotificationEnabled = isNotificationEnabled,
)
