package com.khnsoft.damta.domain.usecase.area

import com.khnsoft.damta.domain.error.AreaError
import com.khnsoft.damta.domain.model.Area
import com.khnsoft.damta.domain.repository.AreaRepository
import com.khnsoft.damta.domain.usecase.RequestValue
import com.khnsoft.damta.domain.usecase.ResponseValue
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

class SearchAreaUseCase @Inject constructor(
    private val areaRepository: AreaRepository
) : ResultUseCase<SearchAreaUseCase.Request, SearchAreaUseCase.Response> {

    override suspend fun invoke(request: Request): Result<Response> {
        if (request.keyword.isBlank()) {
            return Result.failure(AreaError.EmptyKeyword)
        }

        return areaRepository.searchArea(request.keyword).map { areaList ->
            Response(areaList)
        }
    }

    data class Request(
        val keyword: String
    ) : RequestValue

    data class Response(
        val areaList: List<Area>
    ) : ResponseValue
}
