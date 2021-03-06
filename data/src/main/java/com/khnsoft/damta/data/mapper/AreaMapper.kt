package com.khnsoft.damta.data.mapper

import com.khnsoft.damta.data.model.AreaData
import com.khnsoft.damta.domain.model.Area

internal fun Area.toData() = AreaData(
    id = id,
    name = name,
    type = type.toData(),
    place = place.toData(),
    facilities = facilities.map { it.toData() }.toSet(),
    createdDate = createdDate
)

internal fun AreaData.toDomain() = Area(
    id = id,
    name = name,
    type = type.toDomain(),
    place = place.toDomain(),
    facilities = facilities.map { it.toDomain() }.toSet(),
    createdDate = createdDate
)
