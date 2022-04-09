package com.khnsoft.damta.remote.model

import com.google.gson.annotations.SerializedName

data class SearchPlaceResponse(
    @SerializedName("documents") val placeList: List<PlaceResponse>
) {

    data class PlaceResponse(
        @SerializedName("place_name") val name: String,
        @SerializedName("x") val x: Double,
        @SerializedName("y") val y: Double,
        @SerializedName("address_name") val address: String?,
        @SerializedName("road_address_name") val roadAddress: String?
    )
}