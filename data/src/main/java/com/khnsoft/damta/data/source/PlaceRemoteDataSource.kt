package com.khnsoft.damta.data.source

import com.khnsoft.damta.data.model.PlaceData

interface PlaceRemoteDataSource {

    suspend fun searchPlace(
        keyword: String,
        page: Int,
        size: Int,
        isAddress: Boolean
    ): Result<List<PlaceData>>
}
