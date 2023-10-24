package com.airbnb.mvrx.hellohilt.screen.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.airbnb.mvrx.hellohilt.screen.component.TimeLineItem
import com.airbnb.mvrx.hellohilt.screen.detail.state.DetailState

@Composable
fun DetailScreen(
    feedSerialNo: Int,
    onBack: () -> Unit
) {
    val vm: DetailViewModel = mavericksViewModel()
    val feed by vm.collectAsState(DetailState::feedEntity)
    val commentList by vm.collectAsState(DetailState::commentList)

    BackHandler {
        onBack()
    }

    LaunchedEffect(key1 = feedSerialNo) {
        vm.requestFeed(feedSerialNo)
        vm.requestCommentList(feedSerialNo)
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        feed()?.let { feedEntity ->
            item {
                TimeLineItem(
                    modifier = Modifier.fillMaxWidth(),
                    feedEntity = feedEntity,
                    onClickLike = vm::clickLike
                )
            }
        }


        items(commentList() ?: listOf(), key = { it.commentSerialNo }) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = it.comment
                )
                Text(text = it.userName)
            }
        }

    }
}