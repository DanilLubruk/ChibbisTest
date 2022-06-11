package com.example.chibbistest.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.chibbistest.R
import com.example.chibbistest.ui.Screens

sealed class BottomNavigationScreens(
    val route: String,
    val resourceId: Int,
    val icon: ImageVector
) {
    object Restaurants : BottomNavigationScreens(
        Screens.restaurantsListScreen,
        R.string.restaurants_title,
        Icons.Filled.Restaurant
    )

    object Hits :
        BottomNavigationScreens(Screens.hitsListScreen, R.string.hits_title, Icons.Filled.Favorite)

    object Reviews : BottomNavigationScreens(
        Screens.reviewsListScreen,
        R.string.reviews_title,
        Icons.Filled.RateReview
    )

    companion object {
        fun getScreens(): List<BottomNavigationScreens> = listOf(
            Restaurants,
            Hits,
            Reviews
        )
    }
}
