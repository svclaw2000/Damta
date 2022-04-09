package com.khnsoft.damta.remote.source

import com.khnsoft.damta.data.model.PlaceData
import com.khnsoft.damta.data.source.PlaceRemoteDataSource
import com.khnsoft.damta.remote.api.KakaoApiService
import com.khnsoft.damta.remote.mapper.toData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PlaceRemoteDataSourceImpl @Inject constructor(
    private val kakaoApi: KakaoApiService
) : PlaceRemoteDataSource {

    override suspend fun searchPlace(
        keyword: String,
        page: Int,
        size: Int,
        isAddress: Boolean
    ): Result<List<PlaceData>> = runCatching {
        withContext(Dispatchers.IO) {
            if (isAddress) {
                kakaoApi.searchAddress(keyword = keyword, page = page, size = size)
                    .placeList
                    .map { it.toData() }
            } else {
                kakaoApi.searchPlace(keyword = keyword, page = page, size = size)
                    .placeList
                    .map { it.toData() }
            }
        }
    }
}
