package com.example.chibbistest.ui.listscreens

import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.example.chibbistest.R
import com.example.chibbistest.utils.GuiUtils

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

                val viewModel: RestaurantsListViewModel =
                    viewModel(factory = RestaurantsListViewModelFactory())
                var screenState: RestaurantsListState by remember {
                    mutableStateOf(
                        RestaurantsListState.Loading
                    )
                }

                val lifecycle = LocalLifecycleOwner.current
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
                        ListView((screenState as RestaurantsListState.Loaded))
                    }
                    is RestaurantsListState.Loading -> {
                        LoadingView()
                    }
                    is RestaurantsListState.Error -> {
                        ErrorView((screenState as RestaurantsListState.Error))
                    }
                }
            }
        }
    }

    @Composable
    fun ListView(screenState: RestaurantsListState.Loaded) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(UiConsts.screenPadding),
            modifier = Modifier
                .padding(UiConsts.screenPadding)
                .fillMaxWidth()
        ) {
            itemsIndexed(screenState.restaurants) { index, restaurant ->
                RestaurantListItem(restaurant)
            }
        }
    }

    @Composable
    fun RestaurantListItem(restaurant: Restaurant) {
        Card(elevation = 10.dp) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(UiConsts.screenPadding)
            ) {
                Row {
                    Column {
                        GlideImage(
                            modifier = Modifier
                                .width(UiConsts.listItemImageSize)
                                .height(UiConsts.listItemImageSize),
                            imageModel = restaurant.logoUrl,
                            contentScale = ContentScale.Crop,
                            placeHolder = Icons.Outlined.BrokenImage,
                            error = Icons.Outlined.BrokenImage,
                        )
                    }
                    Spacer(modifier = Modifier.width(UiConsts.screenPadding))
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = restaurant.name,
                            fontSize = UiConsts.textMediumSize,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                        Text(
                            text = restaurant.specializationsString,
                            style = TextStyle(
                                fontSize = UiConsts.textSmallSize,
                                color = androidx.compose.ui.graphics.Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier
                                    .height(UiConsts.smallImageSize)
                                    .width(UiConsts.smallImageSize),
                                imageVector = Icons.Filled.ThumbUp,
                                contentDescription = "",
                                tint = androidx.compose.ui.graphics.Color.Green
                            )
                            Spacer(modifier = Modifier.width(UiConsts.listItemSmallMargin))
                            Text(
                                text = restaurant.positiveReviews,
                                style = TextStyle(
                                    fontSize = UiConsts.textSmallSize,
                                    fontWeight = FontWeight.Bold,
                                    color = androidx.compose.ui.graphics.Color.Green
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                        Text(
                            text = restaurant.reviewsCount,
                            style = TextStyle(
                                fontSize = UiConsts.textSmallSize,
                                color = androidx.compose.ui.graphics.Color.Gray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                Divider(thickness = 1.dp, color = androidx.compose.ui.graphics.Color.Gray)
                Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier
                                .height(UiConsts.mediumImageSize)
                                .width(UiConsts.mediumImageSize),
                            imageVector = Icons.Filled.MonetizationOn,
                            contentDescription = "",
                            tint = androidx.compose.ui.graphics.Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(UiConsts.listItemSmallMargin))
                        Text(
                            text = restaurant.minCostString,
                            style = TextStyle(fontSize = UiConsts.textMediumSize),
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier
                                .height(UiConsts.mediumImageSize)
                                .width(UiConsts.mediumImageSize),
                            imageVector = Icons.Filled.DeliveryDining,
                            contentDescription = "",
                            tint = androidx.compose.ui.graphics.Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(UiConsts.listItemSmallMargin))
                        Text(
                            text = restaurant.deliveryCostString,
                            style = TextStyle(fontSize = UiConsts.textMediumSize)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier
                                .height(UiConsts.mediumImageSize)
                                .width(UiConsts.mediumImageSize),
                            imageVector = Icons.Filled.Schedule,
                            contentDescription = "",
                            tint = androidx.compose.ui.graphics.Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(UiConsts.listItemSmallMargin))
                        Text(
                            text = restaurant.deliveryTimeString,
                            style = TextStyle(fontSize = UiConsts.textMediumSize),
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun LoadingView() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(UiConsts.circularProgressBarSize)
                    .width(UiConsts.circularProgressBarSize),
                strokeWidth = 2.dp,
            )
        }
    }

    @Composable
    fun ErrorView(screenState: RestaurantsListState.Error) {
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.message_error_obtaining_data),
                fontSize = UiConsts.textMediumSize
            )
            GuiUtils.showShortToast(
                context,
                screenState.message
            )
        }
    }
}