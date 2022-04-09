package com.khnsoft.damta.domain.error

sealed class PlaceError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object EmptyKeyword : PlaceError()
}
