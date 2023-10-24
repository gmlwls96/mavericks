package com.airbnb.mvrx.hellohilt.repository

import com.airbnb.mvrx.hellohilt.entity.BannerEntity
import com.airbnb.mvrx.hellohilt.entity.BaseEntity
import com.airbnb.mvrx.hellohilt.entity.FeedEntity
import com.airbnb.mvrx.hellohilt.entity.FeedListEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FakeFeedRepositoryImpl : FeedRepository {
    override suspend fun getFeedList(): Flow<FeedListEntity> =
        flow {
            delay(Random.nextLong(100,2000))
            emit(FeedListEntity(feedList))
        }

    override suspend fun getBanner(): Flow<BannerEntity> =
        flow {
            delay(100)
            emit(bannerList[Random.nextInt(0, 3)])
        }

    override suspend fun postLike(feedSerialNo: Int, like: Boolean): Flow<BaseEntity> =
        flow {
            delay(100)
            if (feedSerialNo <= feedList.size) {
                feedList = feedList.makeNewFeedList(feedSerialNo, like)
                emit(BaseEntity(""))
            } else {
                throw Throwable("feedSerialNo does not exist")
            }
        }

    private fun List<FeedEntity>.makeNewFeedList(index: Int, like: Boolean) =
        this.toMutableList().let {
            val prev = it[index]
            it[index] = prev.copy(isLike = like, likeCount = prev.likeCount + if (like) 1 else -1)
            it
        }

    companion object {
        var feedList = listOf(
            FeedEntity(
                feedSerialNo = 0,
                userSerialNo = 1,
                userName = "test1",
                imageUrl = "",
                feedText = "number 1",
                isLike = false,
                likeCount = 1
            ),
            FeedEntity(
                feedSerialNo = 1,
                userSerialNo = 1,
                userName = "test1",
                imageUrl = "",
                feedText = "hi~",
                isLike = false,
                likeCount = 1
            ),
            FeedEntity(
                feedSerialNo = 2,
                userSerialNo = 1,
                userName = "test1",
                imageUrl = "",
                feedText = "mvi test",
                isLike = true,
                likeCount = 1
            ),
            FeedEntity(
                feedSerialNo = 3,
                userSerialNo = 1,
                userName = "test1",
                imageUrl = "",
                feedText = "fun~",
                isLike = false,
                likeCount = 1
            ),

            FeedEntity(
                feedSerialNo = 4,
                userSerialNo = 2,
                userName = "test2",
                imageUrl = "",
                feedText = "fund 소개글",
                isLike = true,
                likeCount = 100
            ),
            FeedEntity(
                feedSerialNo = 5,
                userSerialNo = 2,
                userName = "test2",
                imageUrl = "",
                feedText = "전문 투자자 개인 법인 적격투자자",
                isLike = false,
                likeCount = 12
            ),
            FeedEntity(
                feedSerialNo = 6,
                userSerialNo = 3,
                userName = "test3",
                imageUrl = "",
                feedText = "Global 주요 투자대상: 글로벌 바이아웃펀드 및 세컨더리 펀드",
                isLike = false,
                likeCount = 9
            ),

            FeedEntity(
                feedSerialNo = 7,
                userSerialNo = 4,
                userName = "user4",
                imageUrl = "",
                feedText = "개인 전문 투자자란?",
                isLike = true,
                likeCount = 100
            ),
            FeedEntity(
                feedSerialNo = 8,
                userSerialNo = 2,
                userName = "test2",
                imageUrl = "",
                feedText = "일반투자자 vs 전문투자자",
                isLike = false,
                likeCount = 12
            ),
            FeedEntity(
                feedSerialNo = 9,
                userSerialNo = 3,
                userName = "test3",
                imageUrl = "",
                feedText = "사모펀드 vs 크라우드펀딩 vs 공모펀드",
                isLike = false,
                likeCount = 9
            )
        )

        val bannerList = listOf(
            BannerEntity(
                bannerImage = "R",
                bannerClickLink = ""
            ),
            BannerEntity(
                bannerImage = "G",
                bannerClickLink = ""
            ),
            BannerEntity(
                bannerImage = "B",
                bannerClickLink = ""
            )
        )

        private const val FIRST_PAGE = 0
        private const val LAST_PAGE = 2
    }
}