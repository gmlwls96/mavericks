package com.airbnb.mvrx.hellohilt.repository

import com.airbnb.mvrx.hellohilt.entity.BannerEntity
import com.airbnb.mvrx.hellohilt.entity.BaseEntity
import com.airbnb.mvrx.hellohilt.entity.FeedListEntity
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    suspend fun getFeedList(): Flow<FeedListEntity>
    suspend fun getBanner(): Flow<BannerEntity>
    suspend fun postLike(feedSerialNo: Int, like: Boolean): Flow<BaseEntity>
}