package com.khnsoft.damta.domain.request

import java.time.LocalDate

data class SignUpRequest(
    val username: String,
    val password: String,
    val nickname: String,
    val birthday: LocalDate,
    val email: String
) : Request