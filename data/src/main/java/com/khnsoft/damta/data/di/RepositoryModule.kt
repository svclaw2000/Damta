package com.khnsoft.damta.data.di

import com.khnsoft.damta.data.repository.AreaRepositoryImpl
import com.khnsoft.damta.data.repository.PlaceRepositoryImpl
import com.khnsoft.damta.data.repository.UserRepositoryImpl
import com.khnsoft.damta.domain.repository.AreaRepository
import com.khnsoft.damta.domain.repository.PlaceRepository
import com.khnsoft.damta.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindAreaRepository(
        repository: AreaRepositoryImpl
    ): AreaRepository

    @Binds
    abstract fun bindPlaceRepository(
        repository: PlaceRepositoryImpl
    ): PlaceRepository
}
