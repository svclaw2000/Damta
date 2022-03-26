package com.khnsoft.damta.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.khnsoft.damta.local.model.UserDto

@Dao
internal interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserDto)
}