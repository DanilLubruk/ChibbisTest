package com.example.chibbistest.ui.listscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.chibbistest.ui.components.AppBar
import com.example.chibbistest.ui.navigation.BottomNavigationScreens

object ReviewsListScreen {

    @Composable
    fun Screen() {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                AppBar.AppBar(
                    title = stringResource(id = BottomNavigationScreens.Reviews.resourceId),
                    icon = BottomNavigationScreens.Reviews.icon
                )

                Text(text = "Reviews")
            }
        }
    }
}