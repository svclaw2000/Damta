package com.khnsoft.damta.remote.di

import com.khnsoft.damta.data.source.PlaceRemoteDataSource
import com.khnsoft.damta.remote.source.PlaceRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindPlaceRemoteDataSource(
        source: PlaceRemoteDataSourceImpl
    ): PlaceRemoteDataSource
}
