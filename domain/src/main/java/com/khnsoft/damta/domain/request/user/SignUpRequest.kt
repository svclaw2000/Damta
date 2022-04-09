package com.khnsoft.damta.domain.request.user

import com.khnsoft.damta.domain.request.Request
import java.time.LocalDate

data class SignUpRequest(
    val username: String,
    val password: String,
    val nickname: String,
    val birthday: LocalDate,
    val email: String
) : Request
