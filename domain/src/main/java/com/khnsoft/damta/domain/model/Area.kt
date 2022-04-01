package com.khnsoft.damta.domain.model

import java.time.LocalDateTime

data class Area(
    val id: Int = 0,
    val name: String,
    val type: AreaType,
    val place: Place,
    val facilities: Set<Facility>,
    val createdDate: LocalDateTime = LocalDateTime.now()
)
