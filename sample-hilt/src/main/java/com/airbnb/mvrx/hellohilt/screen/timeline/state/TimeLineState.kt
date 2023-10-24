package com.airbnb.mvrx.hellohilt.screen.timeline.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.hellohilt.entity.BannerEntity
import com.airbnb.mvrx.hellohilt.entity.FeedListEntity

data class TimeLineState(
    val isLoading: Boolean = false,
    val banner: BannerEntity = BannerEntity(
        bannerImage = "",
        bannerClickLink = ""
    ),

    val feedList: Async<FeedListEntity> = Uninitialized,

    val toastMsg: String? = null,
) : MavericksState