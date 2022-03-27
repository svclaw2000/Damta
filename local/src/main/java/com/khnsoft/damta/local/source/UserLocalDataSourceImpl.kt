package com.khnsoft.damta.local.source

import android.database.sqlite.SQLiteConstraintException
import com.khnsoft.damta.common.extension.errorMap
import com.khnsoft.damta.data.error.UserErrorData
import com.khnsoft.damta.data.model.UserData
import com.khnsoft.damta.data.source.UserLocalDataSource
import com.khnsoft.damta.local.dao.UserDao
import com.khnsoft.damta.local.mapper.toDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun signUp(
        user: UserData,
        password: String
    ): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            userDao.addUser(user.toDto(password))
        }
    }.errorMap { error ->
        when (error) {
            is SQLiteConstraintException -> UserErrorData.DuplicatedUsername
            else -> Exception(error)
        }
    }

    override suspend fun signIn(
        username: String,
        password: String
    ): Result<Int> = runCatching {
        withContext(Dispatchers.IO) {
            val user = userDao.fetchUserByUsernameAndPassword(username, password)
            user?.id ?: throw UserErrorData.InvalidUsernameOrPassword
        }
    }
}
