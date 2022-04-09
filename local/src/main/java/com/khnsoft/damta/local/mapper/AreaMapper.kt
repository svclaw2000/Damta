package com.khnsoft.damta.local.mapper

import com.khnsoft.damta.data.model.AreaData
import com.khnsoft.damta.data.model.FacilityData
import com.khnsoft.damta.local.model.AreaDto
import com.khnsoft.damta.local.model.FacilityDto

internal fun AreaData.toDto() = AreaDto(
    id = id,
    name = name,
    type = type,
    place = place.toDto(),
    facilities = FacilityDto(
        ashTray = facilities.contains(FacilityData.ASH_TRAY),
        vent = facilities.contains(FacilityData.VENT),
        bench = facilities.contains(FacilityData.BENCH),
        machine = facilities.contains(FacilityData.MACHINE)
    ),
    createdDate = createdDate
)
