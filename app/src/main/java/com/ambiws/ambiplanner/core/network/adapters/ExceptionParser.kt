package com.ambiws.ambiplanner.core.network.adapters

import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.core.network.adapters.model.NetworkErrorData
import com.ambiws.ambiplanner.core.network.adapters.model.StatusCode
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.ServerError
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.UnexpectedServerError
import com.ambiws.ambiplanner.utils.providers.ResourceProvider
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionParser(
    private val resourceProvider: ResourceProvider
) {

    fun parseError(throwable: Throwable): NetworkErrorData {
        return when (throwable) {
            is UnexpectedServerError -> {
                NetworkErrorData(throwable.code, throwable.message)
            }
            is ServerError -> {
                parseServerError(throwable)
            }
            is ConnectException,
            is SocketTimeoutException,
            is SocketException -> {
                NetworkErrorData(
                    StatusCode.CLIENT_TIMEOUT.code,
                    resourceProvider.getString(R.string.unable_to_communicate_to_server)
                )
            }
            is InterruptedIOException,
            is UnknownHostException -> {
                NetworkErrorData(
                    StatusCode.UNAVAILABLE.code,
                    resourceProvider.getString(R.string.no_internet_connection)
                )
            }
            else -> {
                NetworkErrorData(
                    message = throwable.localizedMessage
                        ?: resourceProvider.getString(R.string.unexpected_network_error)
                )
            }
        }
    }

    private fun parseServerError(throwable: ServerError): NetworkErrorData {
        val message = throwable.errorsList?.joinToString(separator = "\n")?.trim()
            .takeIf { !it.isNullOrBlank() }
            ?: throwable.message
        return NetworkErrorData(throwable.code, message)
    }
}
