package com.ambiws.ambiplanner.features.home.ui.list

import android.os.Parcelable
import com.ambiws.ambiplanner.base.list.ItemModel
import kotlinx.parcelize.Parcelize

interface RoutineBaseItemModel : ItemModel

@Parcelize
data class RoutineItemModel(
    override val id: Int,
    val title: String,
    val description: String?,
    val startTime: String?,
    val timeToComplete: String?,
    val isDone: Boolean = false,
    val isNotificationEnabled: Boolean = false,
) : RoutineBaseItemModel, Parcelable
