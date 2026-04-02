package com.ambiws.ambiplanner.features.dashboard.ui.navbar

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.databinding.ViewBottomNavbarBinding
import com.ambiws.ambiplanner.utils.extensions.dp

class BottomNavbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : BottomNavbar, ConstraintLayout(context, attrs) {
    private val binding: ViewBottomNavbarBinding =
        ViewBottomNavbarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private val viewsList = listOf(
        binding.ivHome, binding.ivCalendar, binding.ivSettings
    )

    override var currentActiveTab = BottomItem.HOME

    override var prevActiveTab = BottomItem.HOME

    override var destinationListener: ((activeBottomItem: BottomItem, commitNow: Boolean) -> Unit)? =
        null

    init {
        setActiveTabsColor()
        setActiveTab(currentActiveTab)
        setListeners()
    }

    private fun setActiveTabsColor() {
        BottomItem.HOME.iconResActive = R.drawable.ic_home
        BottomItem.CALENDAR.iconResActive = R.drawable.ic_calendar
        BottomItem.SETTINGS.iconResActive = R.drawable.ic_settings
    }

    private fun setListeners() {
        with(binding) {
            val homeListener = OnClickListener {
                setActiveTab(BottomItem.HOME)
            }
            ivHome.setOnClickListener(homeListener)

            val listListener = OnClickListener {
                setActiveTab(BottomItem.CALENDAR)
            }
            ivCalendar.setOnClickListener(listListener)

            val profileListener = OnClickListener {
                setActiveTab(BottomItem.SETTINGS)
            }
            ivSettings.setOnClickListener(profileListener)
        }
    }

    override fun setActiveTab(activeBottomItem: BottomItem) {
        destinationListener?.invoke(activeBottomItem, true)
        prevActiveTab = currentActiveTab
        currentActiveTab = activeBottomItem
        val selectorSize = 48.dp
        val activeView = viewsList.find { it.id == activeBottomItem.viewId }
        activeView?.let {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.root)
            constraintSet.clear(binding.menuSelector.id)
            constraintSet.constrainWidth(binding.menuSelector.id, selectorSize)
            constraintSet.constrainHeight(binding.menuSelector.id, selectorSize)
            constraintSet.connect(binding.menuSelector.id, ConstraintSet.TOP, it.id, ConstraintSet.TOP, 0)
            constraintSet.connect(binding.menuSelector.id, ConstraintSet.BOTTOM, it.id, ConstraintSet.BOTTOM, 0)
            constraintSet.connect(binding.menuSelector.id, ConstraintSet.RIGHT, it.id, ConstraintSet.RIGHT, 0)
            constraintSet.connect(binding.menuSelector.id, ConstraintSet.LEFT, it.id, ConstraintSet.LEFT, 0)
            constraintSet.applyTo(binding.root)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putInt(SELECTED_TAB_STATE, currentActiveTab.viewId)
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val activeTab = state.getInt(SELECTED_TAB_STATE, R.id.ivHome)
            setActiveTab(BottomItem.findByViewID(activeTab))
            super.onRestoreInstanceState(state.getParcelable(SUPER_STATE))
        }
    }

    companion object {
        private const val SELECTED_TAB_STATE = "selectedTab"
        private const val SUPER_STATE = "superState"
    }
}
