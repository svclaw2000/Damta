package com.khnsoft.damta.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.khnsoft.damta.local.dao.AreaDao
import com.khnsoft.damta.local.dao.UserDao
import com.khnsoft.damta.local.model.AreaDto
import com.khnsoft.damta.local.model.UserDto

@Database(
    entities = [
        UserDto::class,
        AreaDto::class
    ],
    version = 2
)
@TypeConverters(DateConverters::class)
internal abstract class DamtaDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getAreaDao(): AreaDao

    companion object {

        const val DAMTA_DATABASE_NAME = "DamtaDatabase.db"
    }
}
