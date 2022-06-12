package com.example.chibbistest.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chibbistest.R
import com.example.chibbistest.ui.Screens
import com.example.chibbistest.ui.UiConsts
import com.example.chibbistest.ui.listscreens.HitsListScreen
import com.example.chibbistest.ui.listscreens.RestaurantsListScreen
import com.example.chibbistest.ui.listscreens.ReviewsListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
                var showExitDialog by remember { mutableStateOf(false) }
                BackHandler {
                    showExitDialog = true
                }
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.isStatusBarVisible = true
                }
                if (showExitDialog) {
                    YesNoDialog(
                        onOkPress = {
                            showExitDialog = false
                            finish()
                        },
                        onDismissRequest = {
                            showExitDialog = false
                        },
                        questionText = stringResource(id = R.string.message_ask_exit)
                    )
                }
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
        BottomNavigation(
            elevation = 0.dp,
            backgroundColor = colorResource(id = R.color.royalBlue)
        ) {
            when (val currentRoute = currentRoute(navController)) {
                currentRoute ->
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, stringResource(id = screen.resourceId)) },
                            label = { Text(stringResource(id = screen.resourceId)) },
                            selected = currentRoute == screen.route,
                            selectedContentColor = Color.White,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.popBackStack()
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
        navController.currentBackStackEntryAsState().value?.destination?.route

    @Composable
    fun YesNoDialog(
        onOkPress: () -> Unit,
        onDismissRequest: () -> Unit,
        questionText: String
    ) {
        Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = questionText,
                        style = MaterialTheme.typography.body1
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 16.dp, end = 16.dp)
                ) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = stringResource(id = R.string.caption_cancel),
                            style = TextStyle(color = colorResource(id = R.color.royalBlue))
                        )
                    }

                    TextButton(
                        onClick = {
                            onOkPress()
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.caption_ok),
                            style = TextStyle(color = colorResource(id = R.color.royalBlue))
                        )
                    }

                }
            }
        }
    }
}