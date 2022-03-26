package com.khnsoft.damta.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "User",
    indices = [
        Index(value = ["username"], unique = true)
    ]
)
internal data class UserDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val nickname: String,
    val birthday: LocalDate
)