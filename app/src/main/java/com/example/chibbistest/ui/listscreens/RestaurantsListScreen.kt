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

object RestaurantsListScreen {
    @Composable
    fun Screen() {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                AppBar.AppBar(
                    title = stringResource(id = BottomNavigationScreens.Restaurants.resourceId),
                    icon = BottomNavigationScreens.Restaurants.icon
                )

                Text(text = "Restaurants")
            }
        }
    }
}