package com.khnsoft.damta.local.di

import com.khnsoft.damta.local.dao.UserDao
import com.khnsoft.damta.local.database.DamtaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideUserDao(
        database: DamtaDatabase
    ): UserDao = database.getUserDao()
}