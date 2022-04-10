package com.khnsoft.damta.di

import android.util.Base64
import com.khnsoft.damta.common.NdkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Named("kakaoRestApiKey")
    fun provideKakaoRestApiKey(
        ndkProvider: NdkProvider
    ): String = Base64.decode(ndkProvider.getRestApiKey(), Base64.DEFAULT).decodeToString()
}
