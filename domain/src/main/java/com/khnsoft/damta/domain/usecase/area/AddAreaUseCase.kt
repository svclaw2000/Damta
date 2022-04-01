package com.khnsoft.damta.domain.usecase.area

import com.khnsoft.damta.domain.error.AreaError
import com.khnsoft.damta.domain.model.Area
import com.khnsoft.damta.domain.repository.AreaRepository
import com.khnsoft.damta.domain.request.area.AddAreaRequest
import com.khnsoft.damta.domain.response.area.AddAreaResponse
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AddAreaUseCase @Inject constructor(
    private val areaRepository: AreaRepository
) : ResultUseCase<AddAreaRequest, AddAreaResponse> {

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
}