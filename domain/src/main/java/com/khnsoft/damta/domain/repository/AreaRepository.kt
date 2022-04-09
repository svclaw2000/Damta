package com.khnsoft.damta.domain.repository

import com.khnsoft.damta.domain.model.Area

interface AreaRepository {

    suspend fun addArea(area: Area): Result<Int>
}
