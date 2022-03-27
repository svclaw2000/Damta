package com.khnsoft.damta.domain.repository

import com.khnsoft.damta.domain.model.User

interface UserRepository {

    suspend fun signUp(user: User, password: String): Result<Unit>

    suspend fun signIn(username: String, password: String): Result<Unit>
}