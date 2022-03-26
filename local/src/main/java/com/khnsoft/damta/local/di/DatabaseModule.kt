package com.khnsoft.damta.local.di

import android.content.Context
import androidx.room.Room
import com.khnsoft.damta.local.database.DamtaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DamtaDatabase = Room.databaseBuilder(
        context,
        DamtaDatabase::class.java,
        DamtaDatabase.DAMTA_DATABASE_NAME
    ).build()
}