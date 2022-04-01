package com.khnsoft.damta.local.mapper

import com.khnsoft.damta.data.model.PlaceData
import com.khnsoft.damta.local.model.PlaceDto

internal fun PlaceData.toDto() = PlaceDto(
    name = name,
    address = address,
    roadAddress = roadAddress,
    x = x,
    y = y
)