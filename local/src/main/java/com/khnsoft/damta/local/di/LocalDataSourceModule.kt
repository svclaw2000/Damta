package com.khnsoft.damta.local.di

import com.khnsoft.damta.data.source.AreaLocalDataSource
import com.khnsoft.damta.data.source.UserLocalDataSource
import com.khnsoft.damta.local.source.AreaLocalDataSourceImpl
import com.khnsoft.damta.local.source.UserLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindUserLocalDataSource(
        source: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    abstract fun bindAreaLocalDataSource(
        source: AreaLocalDataSourceImpl
    ): AreaLocalDataSource
}
