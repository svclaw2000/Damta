package com.khnsoft.damta.domain.response.place

import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.response.Response

data class SearchPlaceResponse(
    val placeList: List<Place>
) : Response
