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

internal fun AreaDto.toData() = AreaData(
    id = id,
    name = name,
    type = type,
    place = place.toData(),
    facilities = listOfNotNull(
        if (facilities.ashTray) FacilityData.ASH_TRAY else null,
        if (facilities.vent) FacilityData.VENT else null,
        if (facilities.bench) FacilityData.BENCH else null,
        if (facilities.machine) FacilityData.MACHINE else null,
    ).toSet(),
    createdDate = createdDate
)
