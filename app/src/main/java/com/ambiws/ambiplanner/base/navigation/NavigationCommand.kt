package com.ambiws.ambiplanner.base.navigation

import android.net.Uri
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

sealed class NavigationCommand(
    open val hideKeyboard: Boolean
) {

    data class ToUri(
        val uri: Uri,
        override val hideKeyboard: Boolean,
        val navOptions: NavOptions? = null
    ) : NavigationCommand(hideKeyboard)

    data class To(
        val direction: NavDirections,
        val navigationExtras: Navigator.Extras? = null,
        override val hideKeyboard: Boolean = true,
        val navOptions: NavOptions? = null
    ) : NavigationCommand(hideKeyboard)

    data class CompoundNavigationCommand(
        val navigationCommandList: List<NavigationCommand>,
        override val hideKeyboard: Boolean = true
    ) : NavigationCommand(hideKeyboard)

    data class Back(
        override val hideKeyboard: Boolean = true
    ) : NavigationCommand(hideKeyboard)

    data class BackToStart(
        override val hideKeyboard: Boolean = true
    ) : NavigationCommand(hideKeyboard)
}
