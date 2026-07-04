package com.ambiws.ambiplanner.features.home.domain.model

data class Routine(
    val id: Int?,
    val title: String,
    val description: String?,
    val startTime: String?,
    val timeToComplete: String?,
    val isDone: Boolean = false,
    val isNotificationEnabled: Boolean = false,
)
