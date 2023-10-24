package com.airbnb.mvrx.hellohilt.screen.timeline

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.airbnb.mvrx.hellohilt.screen.timeline.state.TimeLineState
import com.airbnb.mvrx.hellohilt.entity.BannerEntity
import com.airbnb.mvrx.hellohilt.entity.FeedEntity
import com.airbnb.mvrx.hellohilt.screen.component.TimeLineItem

@Composable
fun TimelineScreen(
    onClickDetail: (Int) -> Unit
) {
    val vm: TimelineViewModel = mavericksActivityViewModel()
    val state by vm.collectAsState()

    LaunchedEffect(key1 = true) {
        vm.requestList()
    }

    FeedList(vm = vm, onClickDetail = onClickDetail)

    state.toastMsg?.let {
        ToastMsg(
            msg = it,
            vm::removeToastMsg
        )
    }

    if (state.isLoading) {
        ShowProgressBar()
    }
}

@Composable
fun FeedList(
    vm: TimelineViewModel,
    onClickDetail: (Int) -> Unit
) {
    val list by vm.collectAsState(TimeLineState::feedList)
    val banner by vm.collectAsState(TimeLineState::banner)

    Log.i("hjtag", " list : $list")

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(list()?.feedList ?: listOf(), key = { it.feedSerialNo }) { feedEntity ->
                TimeLineItem(
                    feedEntity = feedEntity,
                    showDetail = onClickDetail,
                    onClickLike = vm::clickLike
                )
            }

            item { Banner(banner) }
        }
    }
}

@Composable
fun ToastMsg(
    msg: String,
    removeToast: () -> Unit
) {
    Toast.makeText(LocalContext.current, msg, Toast.LENGTH_SHORT).show()
    removeToast()
}

@Composable
fun Banner(bannerEvent: BannerEntity) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    when (bannerEvent.bannerImage) {
                        "R" -> Color.Red
                        "G" -> Color.Green
                        else -> Color.Blue
                    }
                )
        ) {
            Text(
                text = "Banner~",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ShowProgressBar() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeLineItemPreview() {
    TimeLineItem(
        feedEntity = FeedEntity(
            feedSerialNo = 0,
            userSerialNo = 1,
            userName = "test1",
            imageUrl = "",
            feedText = "number 1",
            isLike = false,
            likeCount = 1
        ),
        showDetail = {}
    ) { _, _ -> }
}