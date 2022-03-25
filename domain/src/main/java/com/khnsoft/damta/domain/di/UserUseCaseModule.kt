package com.khnsoft.damta.domain.di

import com.khnsoft.damta.domain.request.SignUpRequest
import com.khnsoft.damta.domain.response.EmptyResponse
import com.khnsoft.damta.domain.usecase.ResultUseCase
import com.khnsoft.damta.domain.usecase.SignUpUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserUseCaseModule {

    @Binds
    abstract fun bindSignUpUseCase(
        useCase: SignUpUseCase
    ): ResultUseCase<SignUpRequest, EmptyResponse>
}