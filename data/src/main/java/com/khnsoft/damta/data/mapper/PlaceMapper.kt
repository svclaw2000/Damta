package com.khnsoft.damta.data.mapper

import com.khnsoft.damta.data.model.PlaceData
import com.khnsoft.damta.domain.model.Place

internal fun Place.toData() = PlaceData(
    name = name,
    address = address,
    roadAddress = roadAddress,
    x = x,
    y = y
)

internal fun PlaceData.toDomain() = Place(
    name = name,
    address = address,
    roadAddress = roadAddress,
    x = x,
    y = y
)
