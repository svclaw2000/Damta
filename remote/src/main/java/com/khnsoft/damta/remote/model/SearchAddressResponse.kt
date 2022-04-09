package com.khnsoft.damta.remote.model

import com.google.gson.annotations.SerializedName

data class SearchAddressResponse(
    @SerializedName("documents") val placeList: List<PlaceResponse>
) {

    data class PlaceResponse(
        @SerializedName("address_name") val name: String,
        @SerializedName("x") val x: Double,
        @SerializedName("y") val y: Double,
        @SerializedName("address") val address: AddressResponse?,
        @SerializedName("road_address") val roadAddress: AddressResponse?
    ) {

        data class AddressResponse(
            @SerializedName("address_name") val address: String
        )
    }
}