package com.khnsoft.damta.data.repository

import com.khnsoft.damta.common.extension.errorMap
import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.data.error.UserErrorData
import com.khnsoft.damta.data.mapper.toData
import com.khnsoft.damta.data.mapper.toDomain
import com.khnsoft.damta.data.source.UserLocalDataSource
import com.khnsoft.damta.domain.error.UserError
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import java.time.LocalDate
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalDataSource
) : UserRepository {

    private var userId: Int? = null

    override suspend fun signUp(user: User, password: String): Result<Unit> {
        return local.signUp(user.toData(), password).errorMap { error ->
            when (error) {
                is UserErrorData -> error.toDomain()
                else -> Exception(error)
            }
        }
    }

    override suspend fun signIn(username: String, password: String): Result<Unit> {
        return local.signIn(username, password).flatMap { userId ->
            this.userId = userId
            Result.success(Unit)
        }.errorMap { error ->
            when (error) {
                is UserErrorData -> error.toDomain()
                else -> Exception(error)
            }
        }
    }

    override suspend fun updateUser(
        email: String,
        nickname: String,
        birthday: LocalDate
    ): Result<Unit> {
        return local.updateUser(
            userId = userId ?: return Result.failure(UserError.NotSignedIn),
            email = email,
            nickname = nickname,
            birthday = birthday
        ).errorMap { error ->
            when (error) {
                is UserErrorData -> error.toDomain()
                else -> Exception(error)
            }
        }
    }

    override suspend fun fetchCurrentUserData(): Result<User> {
        return local.fetchUserData(
            userId ?: return Result.failure(UserError.NotSignedIn)
        ).flatMap { user ->
            Result.success(user.toDomain())
        }.errorMap { error ->
            when (error) {
                is UserErrorData -> error.toDomain()
                else -> Exception(error)
            }
        }
    }
}