package com.khnsoft.damta.data.mapper

import com.khnsoft.damta.data.error.UserErrorData
import com.khnsoft.damta.domain.error.UserError

internal fun UserErrorData.toDomain() = when (this) {
    is UserErrorData.DuplicatedUsername -> UserError.DuplicatedUsername
    is UserErrorData.InvalidUsernameOrPassword -> UserError.InvalidUsernameOrPassword
}