package com.airbnb.mvrx.hellohilt

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.airbnb.mvrx.hellohilt.screen.detail.DetailScreen
import com.airbnb.mvrx.hellohilt.screen.nav.Navigation
import com.airbnb.mvrx.hellohilt.screen.nav.navigateToDetail
import com.airbnb.mvrx.hellohilt.screen.timeline.TimelineScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Box(modifier = Modifier.weight(1f)) {
                    ScreenNavHost()
                }
            }
        }
    }

}@Composable
fun ScreenNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "timeline") {
        composable(
            route = Navigation.Routes.TIMELINE
        ) {
            TimelineScreen { feedId -> navController.navigateToDetail(feedId) }
        }

        composable(
            route = Navigation.Routes.DETAIL,
            arguments = listOf(navArgument(name = Navigation.Args.FEED_ID) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val feedId = requireNotNull(backStackEntry.arguments?.getInt(Navigation.Args.FEED_ID))
            DetailScreen(feedId = feedId) { navController.popBackStack() }
        }
    }
}
