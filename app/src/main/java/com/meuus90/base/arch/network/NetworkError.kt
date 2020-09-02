package com.meuus90.base.arch.network

data class NetworkError(val errorType: String?, val message: String = ERROR_DEFAULT) {

    companion object {
        const val ERROR_DEFAULT = "An unexpected error has occurred"
        const val ERROR_SERVICE_UNAVAILABLE = 503
    }
}