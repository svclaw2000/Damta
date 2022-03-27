package com.khnsoft.damta.local.model

import java.time.LocalDate

data class UserInfoDto(
    val id: Int,
    val email: String,
    val nickname: String,
    val birthday: LocalDate
)