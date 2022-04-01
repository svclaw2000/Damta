package com.khnsoft.damta.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khnsoft.damta.data.model.AreaTypeData
import java.time.LocalDateTime

@Entity(tableName = "SmokingArea")
data class AreaDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: AreaTypeData,
    @Embedded(prefix = "place_")
    val place: PlaceDto,
    @Embedded(prefix = "facility_")
    val facilities: FacilityDto,
    val createdDate: LocalDateTime
)