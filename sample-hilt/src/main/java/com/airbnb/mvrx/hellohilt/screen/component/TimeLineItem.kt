package com.airbnb.mvrx.hellohilt.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.hellohilt.entity.FeedEntity

@Composable
fun TimeLineItem(
    modifier: Modifier = Modifier,
    feedEntity: FeedEntity,
    showDetail: (Int) -> Unit = {},
    onClickLike: (Int, Boolean) -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { showDetail(feedEntity.feedSerialNo) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f)
                .background(color = Color.Black)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text("${feedEntity.userName} : ")
            Text(
                modifier = Modifier.weight(1f),
                text = feedEntity.feedText
            )
        }

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                modifier = Modifier.clickable {
                    onClickLike(
                        feedEntity.feedSerialNo,
                        !feedEntity.isLike
                    )
                },
                imageVector = if (feedEntity.isLike) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null
            )
            Text(text = "\t${feedEntity.likeCount}")
        }

    }
}
