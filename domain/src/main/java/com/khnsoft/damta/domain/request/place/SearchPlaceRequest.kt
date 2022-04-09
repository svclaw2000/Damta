package com.khnsoft.damta.domain.request.place

import com.khnsoft.damta.domain.request.Request

data class SearchPlaceRequest(
    val keyword: String,
    val page: Int,
    val size: Int,
    val isAddress: Boolean = false
) : Request
