package com.ambiws.ambiplanner.core.network.adapters.model.exceptions

import java.io.IOException

class UnexpectedNetworkError(cause: Throwable) : IOException(cause)
