package com.khnsoft.damta.data.repository

import com.khnsoft.damta.common.extension.errorMap
import com.khnsoft.damta.data.error.ErrorData
import com.khnsoft.damta.data.mapper.toDomain
import com.khnsoft.damta.data.source.PlaceRemoteDataSource
import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.repository.PlaceRepository
import javax.inject.Inject

internal class PlaceRepositoryImpl @Inject constructor(
    private val remote: PlaceRemoteDataSource
) : PlaceRepository {

    override suspend fun searchPlace(
        keyword: String,
        page: Int,
        size: Int,
        isAddress: Boolean
    ): Result<List<Place>> {
        return remote.searchPlace(
            keyword = keyword,
            page = page,
            size = size,
            isAddress = isAddress
        ).map { placeList ->
            placeList.map { it.toDomain() }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}
