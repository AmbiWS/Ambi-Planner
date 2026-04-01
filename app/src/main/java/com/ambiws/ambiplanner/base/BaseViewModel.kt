package com.ambiws.ambiplanner.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ambiws.ambiplanner.base.navigation.NavigationCommand
import com.ambiws.ambiplanner.base.navigation.ViewModelNavigation
import com.ambiws.ambiplanner.core.network.adapters.ExceptionParser
import com.ambiws.ambiplanner.core.network.adapters.model.NetworkErrorData
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.UnauthorizedServerError
import com.ambiws.ambiplanner.utils.loge
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    /*
     *  Navigation
     */

    protected val navigation by lazy { ViewModelNavigation() }
    val navigationCommand: LiveData<NavigationCommand> by lazy { navigation.navigationCommand }

    fun navigateBack(hideKeyboard: Boolean = true) = navigation.navigateBack(hideKeyboard)

    /*
     *  Data
     */

    @Inject
    lateinit var exceptionParser: ExceptionParser

    private val defaultExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        loge(throwable.message, throwable)
        setError(throwable)
    }

    protected val mainContext: CoroutineContext = Dispatchers.Main
    protected val ioContext: CoroutineContext = Dispatchers.IO

    private val _stateLiveEvent: MutableLiveData<UiState> = MutableLiveData()
    val stateLiveEvent: LiveData<UiState> = _stateLiveEvent

    protected fun setError(throwable: Throwable) {
        when (throwable) {
            is UnauthorizedServerError -> {
                // Implement logout or authorizer interceptor if necessary
                navigation.navigateBackToStart(hideKeyboard = true)
            }
            else -> {
                _stateLiveEvent.value = UiState.Error(exceptionParser.parseError(throwable))
            }
        }
    }

    /*
     *  Core
     */

    protected fun launch(
        coroutineContext: CoroutineContext = mainContext,
        showDefaultLoader: Boolean = false,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(context = coroutineContext + defaultExceptionHandler) {
            if (showDefaultLoader) {
                _stateLiveEvent.value = UiState.Loading
            }
            this.block()
            _stateLiveEvent.value = UiState.Success
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val error: NetworkErrorData) : UiState()
}
