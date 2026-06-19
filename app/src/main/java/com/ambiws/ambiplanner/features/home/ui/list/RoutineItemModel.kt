package com.ambiws.ambiplanner.features.home.ui.list

import com.ambiws.ambiplanner.base.list.ItemModel

interface RoutineBaseItemModel : ItemModel

data class RoutineItemModel(
    val id: Int,
    val title: String,
    val description: String?,
    val startTime: String?,
    val timeToComplete: String?,
) : RoutineBaseItemModel
