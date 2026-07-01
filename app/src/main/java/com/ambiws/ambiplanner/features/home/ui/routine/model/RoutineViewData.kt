package com.ambiws.ambiplanner.features.home.ui.routine.model

data class RoutineViewData(
    val id: Int?,
    val title: String,
    val description: String?,
    val startTime: String?,
    val timeToComplete: String?,
    val isDone: Boolean = false,
)
