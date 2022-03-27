package com.khnsoft.damta.domain.error

sealed class UserError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object InvalidUsername : UserError()

    object InvalidPassword : UserError()

    object InvalidEmail : UserError()

    object InvalidNickname : UserError()

    object DuplicatedUsername : UserError()

    object InvalidUsernameOrPassword : UserError()
}