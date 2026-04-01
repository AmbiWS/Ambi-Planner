package com.ambiws.ambiplanner.core.network.adapters

import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.core.network.adapters.model.ErrorBodyResponse
import com.ambiws.ambiplanner.core.network.adapters.model.StatusCode
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.NotFoundServerError
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.ServerError
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.UnauthorizedServerError
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.UnexpectedNetworkError
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.UnexpectedServerError
import com.ambiws.ambiplanner.core.network.adapters.model.exceptions.ValidationServerError
import com.ambiws.ambiplanner.utils.providers.ResourceProvider
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CallWithErrorHandling(
    private val delegate: Call<Any>
) : Call<Any> by delegate {

    @Inject
    lateinit var resourceProvider: ResourceProvider

    @Inject
    lateinit var gson: Gson

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, handleError(HttpException(response)))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, handleError(t))
            }
        })
    }

    override fun clone(): Call<Any> {
        return CallWithErrorHandling(delegate.clone())
    }

    private fun handleError(throwable: Throwable): Exception {
        return when (throwable) {
            is HttpException -> {
                val rawErrorMsg = throwable.response()?.errorBody()?.string()
                val error = parseError(rawErrorMsg)
                when (throwable.code()) {
                    StatusCode.UNAUTHORIZED.code -> {
                        UnauthorizedServerError(
                            message = error.message,
                            cause = throwable
                        )
                    }
                    StatusCode.NOT_FOUND.code -> {
                        NotFoundServerError(
                            message = error.message,
                            cause = throwable,
                            errorsList = error.errorsList
                        )
                    }
                    StatusCode.VALIDATION.code -> {
                        ValidationServerError(
                            message = error.message,
                            cause = throwable,
                            errorsList = error.errorsList
                        )
                    }
                    in 400..499 -> {
                        ServerError(
                            message = error.message,
                            code = throwable.code(),
                            cause = throwable,
                            errorsList = error.errorsList,
                            headers = throwable.response()?.headers()?.toMap()
                        )
                    }
                    else -> {
                        UnexpectedServerError(throwable.code())
                    }
                }
            }
            is IOException -> {
                throwable
            }
            else -> {
                UnexpectedNetworkError(throwable)
            }
        }
    }

    private fun parseError(rawError: String?): ErrorBodyResponse {
        return try {
            gson.fromJson(rawError, ErrorBodyResponse::class.java).let {
                if (it.message.isBlank()) {
                    it.copy(message = resourceProvider.getString(R.string.unexpected_server_error))
                } else {
                    it
                }
            }
        } catch (e: Exception) {
            ErrorBodyResponse(resourceProvider.getString(R.string.unexpected_server_error))
        }
    }
}
