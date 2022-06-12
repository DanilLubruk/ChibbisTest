package com.example.chibbistest.ui.listscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chibbistest.data.models.Restaurant
import com.example.chibbistest.ui.UiConsts
import com.example.chibbistest.ui.components.AppBar
import com.example.chibbistest.ui.navigation.BottomNavigationScreens
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListState
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListViewModel
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListViewModelFactory
import com.skydoves.landscapist.glide.GlideImage
import com.example.chibbistest.R
import com.example.chibbistest.ui.ColorConsts
import com.example.chibbistest.ui.components.ErrorView
import com.example.chibbistest.ui.components.LoadingView

object RestaurantsListScreen {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun Screen() {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            val viewModel: RestaurantsListViewModel =
                viewModel(factory = RestaurantsListViewModelFactory())
            var showSearch by rememberSaveable { mutableStateOf(false) }
            var searchText by rememberSaveable { mutableStateOf("") }
            val setSearchText: (String) -> Unit = {
                searchText = it
                viewModel.onSearchTextChanged(searchText)
            }

            val keyboardController = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }

            AppBar.AppBar(
                title = stringResource(id = BottomNavigationScreens.Restaurants.resourceId),
                icon = BottomNavigationScreens.Restaurants.icon,
                actions = {
                    IconButton(onClick = {
                        setSearchText("")
                        showSearch = !showSearch
                        if (showSearch) {
                            keyboardController?.show()
                        } else {
                            keyboardController?.hide()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )

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

            AnimatedVisibility(visible = showSearch) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(UiConsts.screenPadding)
                        .focusRequester(focusRequester),
                    value = searchText,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    onValueChange = {
                        setSearchText(it)
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = searchText != "",
                        ) {
                            IconButton(onClick = {
                                setSearchText("")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = ""
                                )
                            }

                        }
                    },
                    label = { Text(stringResource(id = R.string.caption_search_by_name)) },
                    textStyle = MaterialTheme.typography.body1,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = colorResource(id = R.color.royalBlue),
                        focusedLabelColor = colorResource(id = R.color.royalBlue),
                        cursorColor = colorResource(id = R.color.royalBlue),
                    )
                )
            }

            when (screenState) {
                is RestaurantsListState.Loaded -> {
                    ListView((screenState as RestaurantsListState.Loaded))
                }
                is RestaurantsListState.Loading -> {
                    LoadingView.LoadingView()
                }
                is RestaurantsListState.Error -> {
                    ErrorView.ErrorView((screenState as RestaurantsListState.Error).message)
                }
            }
        }
    }

    @Composable
    fun ListView(screenState: RestaurantsListState.Loaded) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(UiConsts.screenPadding),
            modifier = Modifier
                .padding(horizontal = UiConsts.screenPadding)
                .fillMaxWidth()
        ) {
            itemsIndexed(screenState.restaurants) { index, restaurant ->
                if (index == 0) {
                    Spacer(modifier = Modifier.height(UiConsts.screenPadding))
                }
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
                            contentScale = ContentScale.Fit,
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
                                color = ColorConsts.secondaryTextColor
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
                                tint = colorResource(id = R.color.deepGreen)
                            )
                            Spacer(modifier = Modifier.width(UiConsts.listItemSmallMargin))
                            Text(
                                text = restaurant.positiveReviewsString,
                                style = TextStyle(
                                    fontSize = UiConsts.textSmallSize,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.deepGreen)
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                        Text(
                            text = restaurant.reviewsCount,
                            style = TextStyle(
                                fontSize = UiConsts.textSmallSize,
                                color = ColorConsts.secondaryTextColor
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
                            tint = ColorConsts.secondaryIconsColor
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
                            tint = ColorConsts.secondaryIconsColor
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
                            tint = ColorConsts.secondaryIconsColor
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
}