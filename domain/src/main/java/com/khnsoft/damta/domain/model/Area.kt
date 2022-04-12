package com.khnsoft.damta.domain.model

import java.time.LocalDateTime

data class Area(
    val id: Int = 0,
    val name: String = "",
    val type: AreaType = AreaType.OPENED,
    val place: Place = Place(),
    val facilities: Set<Facility> = emptySet(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)
