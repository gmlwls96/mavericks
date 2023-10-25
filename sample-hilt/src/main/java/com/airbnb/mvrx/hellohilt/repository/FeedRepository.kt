package com.airbnb.mvrx.hellohilt.repository

import com.airbnb.mvrx.hellohilt.entity.BannerEntity
import com.airbnb.mvrx.hellohilt.entity.BaseEntity
import com.airbnb.mvrx.hellohilt.entity.CommentEntity
import com.airbnb.mvrx.hellohilt.entity.FeedEntity
import com.airbnb.mvrx.hellohilt.entity.FeedListEntity
import kotlinx.coroutines.flow.Flow
import org.w3c.dom.Comment

interface FeedRepository {
    suspend fun getFeedList(): Flow<FeedListEntity>
    suspend fun getBanner(): Flow<BannerEntity>
    suspend fun postLike(feedSerialNo: Int, like: Boolean): Flow<BaseEntity>

    suspend fun getFeed(feedSerialNo: Int): Flow<FeedEntity>

    suspend fun getCommentList(feedSerialNo: Int): Flow<List<CommentEntity>>

    suspend fun postComment(feedSerialNo: Int, comment: String, userSerialNo: Int, userName: String): Flow<BaseEntity>

}