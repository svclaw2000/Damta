package com.khnsoft.damta.data.repository

import com.khnsoft.damta.common.extension.errorMap
import com.khnsoft.damta.data.error.UserErrorData
import com.khnsoft.damta.data.mapper.toData
import com.khnsoft.damta.data.mapper.toDomain
import com.khnsoft.damta.data.source.UserLocalDataSource
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalDataSource
) : UserRepository {

    override suspend fun signUp(user: User, password: String): Result<Unit> {
        return local.signUp(user.toData(), password).errorMap { error ->
            when (error) {
                is UserErrorData -> error.toDomain()
                else -> Exception(error)
            }
        }
    }

    override suspend fun signIn(username: String, password: String): Result<Unit> {
        return local.signIn(username, password)
    }
}