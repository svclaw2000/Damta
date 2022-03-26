package com.khnsoft.damta.data.repository

import com.khnsoft.damta.data.mapper.toData
import com.khnsoft.damta.data.source.UserLocalDataSource
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalDataSource
) : UserRepository {

    override suspend fun signUp(user: User, password: String): Result<Unit> {
        return local.signUp(user.toData(), password)
    }
}