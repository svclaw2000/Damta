package com.khnsoft.damta.domain.error

sealed class UserError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object InvalidUsername : UserError()

    object InvalidPassword : UserError()

    object InvalidEmail : UserError()

    object InvalidNickname : UserError()

    data class DuplicatedUsername(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserError(message, cause)

    data class InvalidUsernameOrPassword(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserError(message, cause)

    object NotSignedIn : UserError()

    data class NoSuchUser(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserError(message, cause)
}
