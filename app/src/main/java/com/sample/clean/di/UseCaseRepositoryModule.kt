package com.sample.clean.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.clean.data.repository.CommunityHomeRepository
import com.sample.clean.data.repository.CommunityRepository
import com.sample.clean.data.entity.CommunityHomeUseCase
import com.sample.core.domain.executor.PostExecutionThread
import com.sample.core.domain.entity.GsonProvider
import com.sample.core.util.PreferenceManager
import com.sample.clean.helper.HelperUiThread
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat(GsonProvider.ISO_8601_DATE_FORMAT).create()
    }

    @Provides
    @Singleton
    fun provideGsonProvider(): GsonProvider = GsonProvider()

    @Provides
    @Singleton
    fun provideCommunityUseCase(
        communityService: CommunityRepository,
        postExecutionThread: PostExecutionThread
    ) = CommunityHomeUseCase(communityService, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext appContext: Context) =
        PreferenceManager(appContext)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindPostExecutionThread(uiThread: HelperUiThread): PostExecutionThread

    @Binds
    abstract fun bindCommunityDataRepository(communityHomeRepository: CommunityHomeRepository): CommunityRepository
}