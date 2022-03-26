package com.khnsoft.damta.data.model

import java.time.LocalDate

data class UserData(
    val id: Int,
    val username: String,
    val nickname: String,
    val birthday: LocalDate,
    val email: String
)