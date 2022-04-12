package com.khnsoft.damta.data.repository

import com.khnsoft.damta.common.extension.errorMap
import com.khnsoft.damta.data.error.ErrorData
import com.khnsoft.damta.data.mapper.toData
import com.khnsoft.damta.data.mapper.toDomain
import com.khnsoft.damta.data.source.AreaLocalDataSource
import com.khnsoft.damta.domain.model.Area
import com.khnsoft.damta.domain.repository.AreaRepository
import javax.inject.Inject

internal class AreaRepositoryImpl @Inject constructor(
    private val local: AreaLocalDataSource
) : AreaRepository {

    override suspend fun addArea(area: Area): Result<Int> {
        return local.addArea(area.toData()).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun searchArea(keyword: String): Result<List<Area>> {
        return local.searchArea(keyword).map { areaList ->
            areaList.map { it.toDomain() }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}
