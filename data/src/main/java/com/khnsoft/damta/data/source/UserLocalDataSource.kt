package com.khnsoft.damta.data.source

import com.khnsoft.damta.data.model.UserData
import java.time.LocalDate

interface UserLocalDataSource {

    suspend fun signUp(user: UserData, password: String): Result<Unit>

    suspend fun signIn(username: String, password: String): Result<Int>

    suspend fun updateUser(
        userId: Int,
        email: String,
        nickname: String,
        birthday: LocalDate
    ): Result<Unit>

    suspend fun fetchUserData(userId: Int): Result<UserData>
}
