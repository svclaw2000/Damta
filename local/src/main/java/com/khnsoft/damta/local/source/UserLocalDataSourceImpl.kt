package com.khnsoft.damta.local.source

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
    }
}
