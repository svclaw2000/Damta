package com.khnsoft.damta.domain.repository

import com.khnsoft.damta.domain.model.User
import java.time.LocalDate

interface UserRepository {

    suspend fun signUp(user: User, password: String): Result<Unit>

    suspend fun signIn(username: String, password: String): Result<Unit>

    suspend fun updateUser(email: String, nickname: String, birthday: LocalDate): Result<Unit>

    suspend fun fetchCurrentUserData(): Result<User>
}
