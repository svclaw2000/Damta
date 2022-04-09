package com.khnsoft.damta.domain.request.user

import com.khnsoft.damta.domain.request.Request
import java.time.LocalDate

data class UpdateUserRequest(
    val nickname: String,
    val email: String,
    val birthday: LocalDate
) : Request
