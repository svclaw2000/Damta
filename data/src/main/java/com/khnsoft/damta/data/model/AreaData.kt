package com.khnsoft.damta.data.model

import java.time.LocalDateTime

data class AreaData(
    val id: Int = 0,
    val name: String,
    val type: AreaTypeData,
    val place: PlaceData,
    val facilities: Set<FacilityData>,
    val createdDate: LocalDateTime
)
