package com.khnsoft.damta.domain.model

import java.time.LocalDate

data class User(
    val id: Int = 0,
    val username: String,
    val nickname: String,
    val birthday: LocalDate,
    val email: String
)