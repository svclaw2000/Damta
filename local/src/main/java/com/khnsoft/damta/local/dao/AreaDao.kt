package com.khnsoft.damta.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khnsoft.damta.local.model.AreaDto

@Dao
internal interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addArea(area: AreaDto): Long

    @Query("SELECT * FROM SmokingArea WHERE name LIKE '%'||:keyword||'%' OR place_name LIKE '%'||:keyword||'%' OR place_address LIKE '%'||:keyword||'%' OR place_roadAddress LIKE '%'||:keyword||'%'")
    suspend fun searchArea(keyword: String): List<AreaDto>
}
