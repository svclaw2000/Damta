package com.khnsoft.damta.domain.usecase.place

import com.khnsoft.damta.domain.error.PlaceError
import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.repository.PlaceRepository
import com.khnsoft.damta.domain.usecase.RequestValue
import com.khnsoft.damta.domain.usecase.ResponseValue
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

class SearchPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) : ResultUseCase<SearchPlaceUseCase.Request, SearchPlaceUseCase.Response> {

    override suspend fun invoke(request: Request): Result<Response> {
        if (request.keyword.isBlank()) {
            return Result.failure(PlaceError.EmptyKeyword)
        }

        return placeRepository.searchPlace(
            keyword = request.keyword,
            page = request.page,
            size = request.size,
            isAddress = request.isAddress
        ).map { placeList ->
            Response(placeList)
        }
    }

    data class Request(
        val keyword: String,
        val page: Int,
        val size: Int,
        val isAddress: Boolean = false
    ) : RequestValue

    data class Response(
        val placeList: List<Place>
    ) : ResponseValue
}
