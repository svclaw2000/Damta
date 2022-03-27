package com.khnsoft.damta.data.mapper

import com.khnsoft.damta.data.error.ErrorData
import com.khnsoft.damta.data.error.UserErrorData

internal fun ErrorData.toDomain() = when (this) {
    is UserErrorData -> toDomain()
}