package com.ambiws.ambiplanner.base.navigation

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.ambiws.ambiplanner.utils.SingleLiveEvent

open class ViewModelNavigation {

    private val defaultNavOptions = NavOptions.Builder().build()
    val navigationCommand: MutableLiveData<NavigationCommand> = SingleLiveEvent()

    fun navigate(
        direction: NavDirections,
        navigatorExtras: Navigator.Extras? = null,
        hideKeyboard: Boolean = true,
        navOptions: NavOptions? = defaultNavOptions
    ) {
        navigationCommand.value = NavigationCommand.To(
            direction,
            navigatorExtras,
            hideKeyboard,
            navOptions
        )
    }

    fun navigateToUri(
        uri: Uri,
        hideKeyboard: Boolean = true,
        navOptions: NavOptions? = defaultNavOptions
    ) {
        navigationCommand.value = NavigationCommand.ToUri(
            uri,
            hideKeyboard,
            navOptions
        )
    }

    fun compoundNavigation(
        navigationCommandList: List<NavigationCommand>,
        hideKeyboard: Boolean = true
    ) {
        navigationCommand.value = NavigationCommand.CompoundNavigationCommand(
            navigationCommandList,
            hideKeyboard
        )
    }

    fun navigateBack(hideKeyboard: Boolean = true) {
        navigationCommand.value = NavigationCommand.Back(
            hideKeyboard
        )
    }

    fun navigateBackToStart(hideKeyboard: Boolean = true) {
        navigationCommand.value = NavigationCommand.BackToStart(
            hideKeyboard
        )
    }
}
