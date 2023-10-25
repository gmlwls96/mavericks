package com.airbnb.mvrx.hellohilt.screen.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
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
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
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
                Row(modifier = Modifier.padding(10.dp)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = it.comment
                    )
                    Text(text = it.userName)
                }
            }
        }

        val (text, textChange) = remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            value = text,
            onValueChange = textChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                vm.addComment(
                    feedSerialNo = feedSerialNo,
                    comment = text
                )
                textChange("")
                focusManager.clearFocus()
            })
        )
    }
}