package com.khnsoft.damta.domain.di

import com.khnsoft.damta.domain.request.area.AddAreaRequest
import com.khnsoft.damta.domain.response.area.AddAreaResponse
import com.khnsoft.damta.domain.usecase.ResultUseCase
import com.khnsoft.damta.domain.usecase.area.AddAreaUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AreaUseCaseModule {

    @Binds
    abstract fun bindAddAreaUseCase(
        useCase: AddAreaUseCase
    ): ResultUseCase<AddAreaRequest, AddAreaResponse>
}
