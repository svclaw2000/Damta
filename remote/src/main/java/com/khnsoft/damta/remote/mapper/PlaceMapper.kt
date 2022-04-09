package com.khnsoft.damta.remote.mapper

import com.khnsoft.damta.data.model.PlaceData
import com.khnsoft.damta.remote.model.SearchAddressResponse
import com.khnsoft.damta.remote.model.SearchPlaceResponse

internal fun SearchAddressResponse.PlaceResponse.toData() = PlaceData(
    name = name,
    address = address?.address,
    roadAddress = roadAddress?.address,
    x = x,
    y = y
)

internal fun SearchPlaceResponse.PlaceResponse.toData() = PlaceData(
    name = name,
    address = address,
    roadAddress = roadAddress,
    x = x,
    y = y
)
