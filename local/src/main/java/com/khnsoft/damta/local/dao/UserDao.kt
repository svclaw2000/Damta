package com.khnsoft.damta.local.dao

import androidx.room.*
import com.khnsoft.damta.local.model.UserDto
import com.khnsoft.damta.local.model.UserInfoDto

@Dao
internal interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserDto)

    @Query("SELECT * FROM User WHERE username=:username AND password=:password")
    suspend fun fetchUserByUsernameAndPassword(username: String, password: String): UserDto?

    @Update(entity = UserDto::class)
    suspend fun updateUser(userInfo: UserInfoDto): Int

    @Query("SELECT * FROM User WHERE id=:userId")
    suspend fun fetchUserById(
        userId: Int,
    ): UserDto?
}