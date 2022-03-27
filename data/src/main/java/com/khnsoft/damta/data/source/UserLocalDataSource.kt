package com.khnsoft.damta.data.source

import com.khnsoft.damta.data.model.UserData

interface UserLocalDataSource {

    suspend fun signUp(user: UserData, password: String): Result<Unit>

    suspend fun signIn(username: String, password: String): Result<Unit>
}