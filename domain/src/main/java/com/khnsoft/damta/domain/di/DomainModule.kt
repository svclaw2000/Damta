package com.khnsoft.damta.domain.di

import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.common.impl.UserValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DomainModule {

    @Binds
    abstract fun bindUserValidator(
        validator: UserValidatorImpl
    ): UserValidator
}