package com.example.chibbistest.ui.listscreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chibbistest.data.models.Restaurant
import com.example.chibbistest.ui.UiConsts
import com.example.chibbistest.ui.components.AppBar
import com.example.chibbistest.ui.navigation.BottomNavigationScreens
import com.example.chibbistest.ui.viewmodels.RestaurantsListState
import com.example.chibbistest.ui.viewmodels.RestaurantsListViewModel
import com.example.chibbistest.ui.viewmodels.RestaurantsListViewModelFactory
import com.skydoves.landscapist.glide.GlideImage

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

                val lifecycle = LocalLifecycleOwner.current
                val viewModel: RestaurantsListViewModel =
                    viewModel(factory = RestaurantsListViewModelFactory())
                var screenState: RestaurantsListState by remember {
                    mutableStateOf(
                        RestaurantsListState.Loading
                    )
                }

                LaunchedEffect(key1 = Unit) {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.listState.collect { listState ->
                            screenState = listState
                        }
                    }
                }

                LaunchedEffect(key1 = Unit) {
                    viewModel.fetchRestaurants()
                }

                when (screenState) {
                    is RestaurantsListState.Loaded -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(UiConsts.screenPadding)
                                .fillMaxWidth()
                        ) {
                            itemsIndexed((screenState as RestaurantsListState.Loaded).restaurants) { index, restaurant ->
                                RestaurantListItem(restaurant)
                            }
                        }
                    }
                    is RestaurantsListState.Loading -> {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Restaurants")
                        }
                    }
                    is RestaurantsListState.Error -> {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Restaurants")
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun RestaurantListItem(restaurant: Restaurant) {
        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            Text(text = "name: ${restaurant.Name}")
            GlideImage(imageModel = restaurant.Logo, contentScale = ContentScale.Crop)
            Text(text = "logoUrl: ${restaurant.Logo}")
            Text(text = "minCost: ${restaurant.MinCost}")
            Text(text = "deliveryCost: ${restaurant.DeliveryCost}")
            Text(text = "deliveryTime: ${restaurant.DeliveryTime}")
            Text(text = "positiveReviews: ${restaurant.PositiveReviews}")
            Text(text = "reviewsCount: ${restaurant.ReviewsCount}")
            Text(text = "specializations: ${restaurant.Specializations.size}")

            Spacer(modifier = Modifier.height(UiConsts.screenPadding))
        }
    }
}