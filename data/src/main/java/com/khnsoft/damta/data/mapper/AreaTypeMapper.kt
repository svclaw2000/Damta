package com.khnsoft.damta.data.mapper

import com.khnsoft.damta.data.model.AreaTypeData
import com.khnsoft.damta.domain.model.AreaType

internal fun AreaType.toData() = AreaTypeData.values()[ordinal]

internal fun AreaTypeData.toDomain() = AreaType.values()[ordinal]