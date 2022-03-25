package com.khnsoft.damta.utils

enum class KakaoAPI(val remote: String, val method: HttpMethod) {
    SEARCH_PLACE("/v2/local/search/keyword.json", HttpMethod.GET),
    SEARCH_ADDRESS("/v2/local/search/address.json", HttpMethod.GET),
}