package com.khnsoft.damta.domain.usecase.place

import com.khnsoft.damta.domain.error.PlaceError
import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.repository.PlaceRepository
import com.khnsoft.damta.domain.usecase.Request
import com.khnsoft.damta.domain.usecase.Response
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

class SearchPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) : ResultUseCase<SearchPlaceUseCase.SearchPlaceRequest, SearchPlaceUseCase.SearchPlaceResponse> {

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

    data class SearchPlaceRequest(
        val keyword: String,
        val page: Int,
        val size: Int,
        val isAddress: Boolean = false
    ) : Request

    data class SearchPlaceResponse(
        val placeList: List<Place>
    ) : Response
}
