package com.airbnb.mvrx.hellohilt.screen.detail.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.hellohilt.entity.CommentEntity
import com.airbnb.mvrx.hellohilt.entity.FeedEntity

data class DetailState(
    val feedEntity: Async<FeedEntity> = Uninitialized,
    val commentList: Async<List<CommentEntity>> = Uninitialized
) : MavericksState