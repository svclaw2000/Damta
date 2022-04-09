package com.khnsoft.damta.domain.repository

import com.khnsoft.damta.domain.model.Place

interface PlaceRepository {

    suspend fun searchPlace(
        keyword: String,
        page: Int,
        size: Int,
        isAddress: Boolean
    ): Result<List<Place>>
}
