package com.khnsoft.damta.data.source

import com.khnsoft.damta.data.model.AreaData

interface AreaLocalDataSource {

    suspend fun addArea(areaData: AreaData): Result<Int>
}