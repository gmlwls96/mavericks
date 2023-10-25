package com.airbnb.mvrx.hellohilt.screen.detail

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hellohilt.repository.FeedRepository
import com.airbnb.mvrx.hellohilt.screen.detail.state.DetailState
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    @Assisted initState: DetailState,
    private val feedRepository: FeedRepository
) : MavericksViewModel<DetailState>(initState) {
    @AssistedFactory
    interface Factory : AssistedViewModelFactory<DetailViewModel, DetailState> {
        override fun create(state: DetailState): DetailViewModel
    }

    companion object : MavericksViewModelFactory<DetailViewModel, DetailState> by hiltMavericksViewModelFactory()

    fun requestFeed(feedSerialNo: Int) {
        viewModelScope.launch {
            feedRepository.getFeed(feedSerialNo)
                .execute(
                    Dispatchers.IO,
                    retainValue = DetailState::feedEntity
                ) {
                    copy(feedEntity = it)
                }
        }
    }

    fun requestCommentList(feedSerialNo: Int) {
        viewModelScope.launch {
            feedRepository.getCommentList(feedSerialNo)
                .execute(
                    Dispatchers.IO,
                    retainValue = DetailState::commentList
                ) {
                    copy(commentList = it)
                }
        }
    }

    fun clickLike(feedSerialNo: Int, isLike: Boolean) {
        viewModelScope.launch {
            feedRepository.postLike(feedSerialNo, isLike)
                .flatMapLatest {
                    feedRepository.getFeed(feedSerialNo)
                }.execute(
                    Dispatchers.IO,
                    retainValue = DetailState::feedEntity
                ) {
                    copy(feedEntity = it)
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun addComment(
        feedSerialNo: Int,
        comment: String,
        userSerialNo: Int = 0,
        userName: String = "hjtag"
    ) {
        viewModelScope.launch {
            feedRepository.postComment(
                feedSerialNo = feedSerialNo,
                comment = comment,
                userSerialNo = userSerialNo,
                userName = userName
            )
                .flatMapLatest {
                    feedRepository.getCommentList(feedSerialNo)
                }.execute(
                    Dispatchers.IO,
                    retainValue = DetailState::commentList
                ) {
                    copy(commentList = it)
                }
        }
    }
}