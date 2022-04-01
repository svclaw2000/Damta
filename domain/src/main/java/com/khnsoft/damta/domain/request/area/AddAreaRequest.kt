package com.khnsoft.damta.domain.request.area

import com.khnsoft.damta.domain.model.AreaType
import com.khnsoft.damta.domain.model.Facility
import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.request.Request

data class AddAreaRequest(
    val name: String,
    val type: AreaType,
    val place: Place,
    val facilities: Set<Facility>
) : Request