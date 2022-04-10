package com.khnsoft.damta.domain.usecase.area

import com.khnsoft.damta.domain.error.AreaError
import com.khnsoft.damta.domain.model.Area
import com.khnsoft.damta.domain.model.AreaType
import com.khnsoft.damta.domain.model.Facility
import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.repository.AreaRepository
import com.khnsoft.damta.domain.usecase.Request
import com.khnsoft.damta.domain.usecase.Response
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

class AddAreaUseCase @Inject constructor(
    private val areaRepository: AreaRepository
) : ResultUseCase<AddAreaUseCase.AddAreaRequest, AddAreaUseCase.AddAreaResponse> {

    override suspend fun invoke(request: AddAreaRequest): Result<AddAreaResponse> {
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
            AddAreaResponse(areaId)
        }
    }

    data class AddAreaResponse(
        val areaId: Int
    ) : Response

    data class AddAreaRequest(
        val name: String,
        val type: AreaType,
        val place: Place,
        val facilities: Set<Facility>
    ) : Request
}
