package com.khnsoft.damta.domain.usecase.place

import com.khnsoft.damta.domain.error.PlaceError
import com.khnsoft.damta.domain.repository.PlaceRepository
import com.khnsoft.damta.domain.request.place.SearchPlaceRequest
import com.khnsoft.damta.domain.response.place.SearchPlaceResponse
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SearchPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) : ResultUseCase<SearchPlaceRequest, SearchPlaceResponse> {

    override suspend fun invoke(request: SearchPlaceRequest): Result<SearchPlaceResponse> {
        if (request.keyword.isBlank()) {
            return Result.failure(PlaceError.EmptyKeyword)
        }

        return placeRepository.searchPlace(
            keyword = request.keyword,
            page = request.page,
            size = request.size,
            isAddress = request.isAddress
        ).map { placeList ->
            SearchPlaceResponse(placeList)
        }
    }
}
