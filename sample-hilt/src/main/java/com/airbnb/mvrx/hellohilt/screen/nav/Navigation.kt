package com.airbnb.mvrx.hellohilt.screen.nav

import androidx.navigation.NavController

object Navigation {
    object Args {
        const val FEED_ID = "feed_id"
    }

    object Routes {
        const val TIMELINE = "timeline"
        const val DETAIL = "detail/{${Args.FEED_ID}}"
    }
}

fun NavController.navigateToDetail(feedId: Int) {
    navigate(route = "detail/$feedId")
}