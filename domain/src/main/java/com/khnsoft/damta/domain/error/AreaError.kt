package com.khnsoft.damta.domain.error

sealed class AreaError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object EmptyName : AreaError()

    object EmptyKeyword : AreaError()
}
