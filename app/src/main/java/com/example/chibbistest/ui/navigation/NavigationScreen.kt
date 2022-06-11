package com.example.chibbistest.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chibbistest.ui.Screens
import com.example.chibbistest.ui.UiConsts
import com.example.chibbistest.ui.listscreens.HitsListScreen
import com.example.chibbistest.ui.listscreens.RestaurantsListScreen
import com.example.chibbistest.ui.listscreens.ReviewsListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class NavigationScreen : AppCompatActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberAnimatedNavController()

            Scaffold(
                backgroundColor = Color.White,
                bottomBar = {
                    AppBottomNavigation(
                        navController,
                        BottomNavigationScreens.getScreens()
                    )
                }) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screens.restaurantsListScreen
                ) {
                    composable(Screens.restaurantsListScreen) {
                        RestaurantsListScreen.Screen()
                    }
                    composable(Screens.hitsListScreen) {
                        HitsListScreen.Screen()
                    }
                    composable(Screens.reviewsListScreen) {
                        ReviewsListScreen.Screen()
                    }
                }
            }
        }
    }

    @Composable
    private fun AppBottomNavigation(
        navController: NavController,
        items: List<BottomNavigationScreens>
    ) {
        BottomNavigation {
            when (val currentRoute = currentRoute(navController)) { currentRoute ->
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, stringResource(id = screen.resourceId)) },
                        label = { Text(stringResource(id = screen.resourceId)) },
                        selected = currentRoute == screen.route,
                        selectedContentColor = Color.White,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route)
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun currentRoute(navController: NavController): String? =
        navController.currentBackStackEntry?.destination?.route

}