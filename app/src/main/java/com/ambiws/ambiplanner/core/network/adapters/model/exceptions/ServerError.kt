package com.ambiws.ambiplanner.core.network.adapters.model.exceptions

import java.io.IOException

open class ServerError(
    override val message: String,
    val code: Int,
    cause: Throwable? = null,
    val errorsList: List<String>? = null,
    val headers: Map<String, String>? = null
) : IOException(message, cause)
