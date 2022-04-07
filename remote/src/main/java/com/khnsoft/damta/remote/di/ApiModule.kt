package com.khnsoft.damta.remote.di

import com.khnsoft.damta.remote.api.KakaoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideKakaoApiService(
        @Named("kakaoBaseUrl") baseUrl: String,
        okHttpClient: OkHttpClient
    ): KakaoApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create()

    @Provides
    @Named("kakaoBaseUrl")
    fun provideKakaoBaseUrl(): String = "https://dapi.kakao.com"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("kakaoRestApiKey") apiKey: String
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "KakaoAK $apiKey")
                .build()
            chain.proceed(request)
        }
        .build()
}