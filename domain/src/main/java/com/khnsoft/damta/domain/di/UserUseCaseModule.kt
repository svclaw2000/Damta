package com.khnsoft.damta.domain.di

import com.khnsoft.damta.domain.request.SignInRequest
import com.khnsoft.damta.domain.request.SignUpRequest
import com.khnsoft.damta.domain.request.UpdateUserRequest
import com.khnsoft.damta.domain.response.EmptyResponse
import com.khnsoft.damta.domain.response.UpdateUserResponse
import com.khnsoft.damta.domain.usecase.ResultUseCase
import com.khnsoft.damta.domain.usecase.SignInUseCase
import com.khnsoft.damta.domain.usecase.SignUpUseCase
import com.khnsoft.damta.domain.usecase.UpdateUserUseCase
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

    @Binds
    abstract fun bindSignInUseCase(
        useCase: SignInUseCase
    ): ResultUseCase<SignInRequest, EmptyResponse>

    @Binds
    abstract fun bindUpdateUserUseCase(
        useCase: UpdateUserUseCase
    ): ResultUseCase<UpdateUserRequest, UpdateUserResponse>
}