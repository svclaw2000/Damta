package com.khnsoft.damta.data.error

sealed class UserErrorData(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object DuplicatedUsername : UserErrorData()
}