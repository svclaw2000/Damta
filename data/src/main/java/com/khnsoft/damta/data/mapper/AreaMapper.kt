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