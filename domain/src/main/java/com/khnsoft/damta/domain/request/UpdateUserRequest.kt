package com.khnsoft.damta.domain.request

import java.time.LocalDate

data class UpdateUserRequest(
    val nickname: String,
    val email: String,
    val birthday: LocalDate
) : Request