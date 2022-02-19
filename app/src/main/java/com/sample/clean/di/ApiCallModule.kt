package com.sample.clean.di

import com.sample.clean.entity.CommunityHomeService
import com.sample.core.domain.entity.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiCallModule {

    @Provides
    fun provideArticleService(networkUtil: NetworkUtil) =
        networkUtil.create(CommunityHomeService::class.java)
}
