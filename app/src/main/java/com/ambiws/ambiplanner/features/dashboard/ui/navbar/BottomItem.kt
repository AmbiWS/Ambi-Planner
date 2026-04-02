package com.ambiws.ambiplanner.features.dashboard.ui.navbar

import com.ambiws.ambiplanner.R

enum class BottomItem(
    val rootId: Int,
    val viewId: Int,
    var iconResActive: Int,
) {
    HOME(
        1,
        R.id.ivHome,
        R.drawable.ic_home,
    ),
    CALENDAR(
        2,
        R.id.ivCalendar,
        R.drawable.ic_calendar,
    ),
    SETTINGS(
        3,
        R.id.ivSettings,
        R.drawable.ic_settings,
    );

    companion object {
        fun findByViewID(viewId: Int): BottomItem =
            BottomItem.values().firstOrNull { it.viewId == viewId } ?: HOME
    }
}
