package com.khnsoft.damta.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khnsoft.damta.local.dao.UserDao
import com.khnsoft.damta.local.model.UserDto

@Database(
    entities = [UserDto::class],
    version = 1
)
internal abstract class DamtaDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        const val DAMTA_DATABASE_NAME = "DamtaDatabase.db"
    }
}