package com.khnsoft.damta.domain.di

import com.khnsoft.damta.domain.common.HashGenerator
import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.common.impl.HashGeneratorImpl
import com.khnsoft.damta.domain.common.impl.UserValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.MessageDigest

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DomainModule {

    @Binds
    abstract fun bindUserValidator(
        validator: UserValidatorImpl
    ): UserValidator

    @Binds
    abstract fun bindHashGenerator(
        generator: HashGeneratorImpl
    ): HashGenerator

    companion object {

        @Provides
        fun provideDigest(): MessageDigest = MessageDigest.getInstance("SHA-256")
    }
}
