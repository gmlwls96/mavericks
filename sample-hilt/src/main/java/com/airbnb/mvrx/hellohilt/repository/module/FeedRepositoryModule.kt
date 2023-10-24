package com.airbnb.mvrx.hellohilt.repository.module

import com.airbnb.mvrx.hellohilt.repository.FakeFeedRepositoryImpl
import com.airbnb.mvrx.hellohilt.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedRepositoryModule {
    @Provides
    @Singleton
    fun feedRepository(): FeedRepository = FakeFeedRepositoryImpl()
}