package com.khnsoft.damta.di

import android.content.Context
import com.khnsoft.damta.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Named("kakaoRestApiKey")
    fun provideKakaoRestApiKey(
        @ApplicationContext context: Context
    ): String = context.getString(R.string.kakaoRestApiKey)
}
