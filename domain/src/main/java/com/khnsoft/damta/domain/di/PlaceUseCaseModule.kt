package com.khnsoft.damta.domain.di

import com.khnsoft.damta.domain.request.place.SearchPlaceRequest
import com.khnsoft.damta.domain.response.place.SearchPlaceResponse
import com.khnsoft.damta.domain.usecase.ResultUseCase
import com.khnsoft.damta.domain.usecase.place.SearchPlaceUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PlaceUseCaseModule {

    @Binds
    abstract fun bindSearchPlaceUseCase(
        useCase: SearchPlaceUseCase
    ): ResultUseCase<SearchPlaceRequest, SearchPlaceResponse>
}
