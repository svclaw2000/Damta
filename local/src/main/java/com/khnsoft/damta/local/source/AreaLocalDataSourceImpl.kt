package com.khnsoft.damta.local.source

import com.khnsoft.damta.data.model.AreaData
import com.khnsoft.damta.data.source.AreaLocalDataSource
import com.khnsoft.damta.local.dao.AreaDao
import com.khnsoft.damta.local.mapper.toData
import com.khnsoft.damta.local.mapper.toDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

internal class AreaLocalDataSourceImpl @Inject constructor(
    private val areaDao: AreaDao
) : AreaLocalDataSource {

    override suspend fun addArea(
        areaData: AreaData
    ): Result<Int> = runCatching {
        withContext(Dispatchers.IO) {
            areaDao.addArea(
                areaData.toDto().copy(
                    createdDate = LocalDateTime.now()
                )
            ).toInt()
        }
    }

    override suspend fun searchArea(
        keyword: String
    ): Result<List<AreaData>> = runCatching {
        withContext(Dispatchers.IO) {
            areaDao.searchArea(keyword).map { it.toData() }
        }
    }
}
