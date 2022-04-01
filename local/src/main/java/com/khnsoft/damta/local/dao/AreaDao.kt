package com.khnsoft.damta.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.khnsoft.damta.local.model.AreaDto

@Dao
internal interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addArea(area: AreaDto): Long
}