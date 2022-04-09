package com.khnsoft.damta.data.error

sealed class UserErrorData(
    override val message: String? = null,
    override val cause: Throwable? = null
) : ErrorData(message, cause) {

    data class DuplicatedUsername(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserErrorData(message, cause)

    object InvalidUsernameOrPassword : UserErrorData()

    object NoSuchUser : UserErrorData()
}
