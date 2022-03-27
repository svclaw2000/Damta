package com.khnsoft.damta.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khnsoft.damta.local.model.UserDto
import java.time.LocalDate

@Dao
internal interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserDto)

    @Query("SELECT * FROM User WHERE username=:username AND password=:password")
    suspend fun fetchUserByUsernameAndPassword(username: String, password: String): UserDto?

    @Query("UPDATE User SET email=:email, nickname=:nickname, birthday=:birthday WHERE id=:userId")
    suspend fun updateUser(
        userId: Int,
        email: String,
        nickname: String,
        birthday: LocalDate
    )

    @Query("SELECT * FROM User WHERE id=:userId")
    suspend fun fetchUserById(
        userId: Int,
    ): UserDto?
}