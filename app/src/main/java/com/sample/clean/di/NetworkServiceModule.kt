package com.sample.clean.di

import com.sample.clean.BuildConfig
import com.sample.core.domain.entity.GsonProvider
import com.sample.core.domain.entity.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    @Singleton
    fun provideNetworkService(
        gsonProvider: GsonProvider
    ) = NetworkUtil(
        gsonProvider = gsonProvider,
        endPoint = BuildConfig.BASE_URL
    )
}