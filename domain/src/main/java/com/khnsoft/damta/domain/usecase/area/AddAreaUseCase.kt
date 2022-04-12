package com.khnsoft.damta.domain.usecase.area

import com.khnsoft.damta.domain.error.AreaError
import com.khnsoft.damta.domain.model.Area
import com.khnsoft.damta.domain.model.AreaType
import com.khnsoft.damta.domain.model.Facility
import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.repository.AreaRepository
import com.khnsoft.damta.domain.usecase.RequestValue
import com.khnsoft.damta.domain.usecase.ResponseValue
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

class AddAreaUseCase @Inject constructor(
    private val areaRepository: AreaRepository
) : ResultUseCase<AddAreaUseCase.Request, AddAreaUseCase.Response> {

    override suspend fun invoke(request: Request): Result<Response> {
        if (request.name.isBlank()) {
            return Result.failure(AreaError.EmptyName)
        }

        val area = Area(
            name = request.name,
            type = request.type,
            place = request.place,
            facilities = request.facilities
        )

        return areaRepository.addArea(area).map { areaId ->
            Response(areaId)
        }
    }

    data class Request(
        val name: String,
        val type: AreaType,
        val place: Place,
        val facilities: Set<Facility>
    ) : RequestValue

    data class Response(
        val areaId: Int
    ) : ResponseValue
}
