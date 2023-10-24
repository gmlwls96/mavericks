package com.airbnb.mvrx.hellohilt.screen.timeline

import android.util.Log
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.hellohilt.repository.FeedRepository
import com.airbnb.mvrx.hellohilt.screen.timeline.state.TimeLineState
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TimelineViewModel @AssistedInject constructor(
    @Assisted initState: TimeLineState,
    private val feedRepository: FeedRepository
) : MavericksViewModel<TimeLineState>(initState) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<TimelineViewModel, TimeLineState> {
        override fun create(state: TimeLineState): TimelineViewModel
    }

    companion object : MavericksViewModelFactory<TimelineViewModel, TimeLineState> by hiltMavericksViewModelFactory()

    fun requestList() {
        viewModelScope.launch {
            feedRepository.getFeedList()
                .onStart {
                    setState { copy(isLoading = true) }
                }
                .execute(
                    Dispatchers.IO,
                    retainValue = TimeLineState::feedList
                ) {
                    Log.i("hjtag", " in vm, isLoading = false, feedList = $it")
                    copy(isLoading = false, feedList = it)
                }
        }
    }

    fun removeToastMsg() = setState {
        copy(toastMsg = null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clickLike(feedSerialNo: Int, isLike: Boolean) {
        viewModelScope.launch {
            feedRepository.postLike(feedSerialNo = feedSerialNo, like = isLike)
                .flatMapLatest { feedRepository.getFeedList() }
                .execute(retainValue = TimeLineState::feedList) {
                    Log.i("hjtag", "\tclickLike result2 : $it")
                    when (it) {
                        is Fail -> {
                            copy(toastMsg = it.error.message)// error  전달 되는지?
                        }

                        is Success -> {
                            copy(feedList = it, toastMsg = if (isLike) "like feed$feedSerialNo " else "cancle like ")
                        }

                        else -> {
                            copy()
                        }
                    }

                }

        }
    }
}
