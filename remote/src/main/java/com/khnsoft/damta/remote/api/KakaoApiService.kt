package com.khnsoft.damta.remote.api

import com.khnsoft.damta.remote.model.SearchAddressResponse
import com.khnsoft.damta.remote.model.SearchPlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface KakaoApiService {

    @GET("/v2/local/search/address.JSON")
    suspend fun searchAddress(
        @Query("query") keyword: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): SearchAddressResponse

    @GET("/v2/local/search/keyword.JSON")
    suspend fun searchPlace(
        @Query("query") keyword: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): SearchPlaceResponse
}