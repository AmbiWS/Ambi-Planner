package com.ambiws.ambiplanner.core.network.adapters.model

import java.net.HttpURLConnection

enum class StatusCode(val code: Int) {
    SUCCESS(HttpURLConnection.HTTP_OK),
    BAD_REQUEST(HttpURLConnection.HTTP_BAD_REQUEST),
    UNAUTHORIZED(HttpURLConnection.HTTP_UNAUTHORIZED),
    NOT_FOUND(HttpURLConnection.HTTP_NOT_FOUND),
    CLIENT_TIMEOUT(HttpURLConnection.HTTP_CLIENT_TIMEOUT),
    VALIDATION(422),
    INTERNAL_SERVER_ERROR(HttpURLConnection.HTTP_INTERNAL_ERROR),
    UNAVAILABLE(HttpURLConnection.HTTP_UNAVAILABLE)
}
