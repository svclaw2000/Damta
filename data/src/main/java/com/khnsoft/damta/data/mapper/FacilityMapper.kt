package com.khnsoft.damta.data.mapper

import com.khnsoft.damta.data.model.FacilityData
import com.khnsoft.damta.domain.model.Facility

internal fun Facility.toData() = FacilityData.values()[ordinal]

internal fun FacilityData.toDomain() = Facility.values()[ordinal]
