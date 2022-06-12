package com.example.chibbistest.ui.listscreens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chibbistest.data.models.Hit
import com.example.chibbistest.ui.ColorConsts
import com.example.chibbistest.ui.UiConsts
import com.example.chibbistest.ui.components.AppBar
import com.example.chibbistest.ui.components.ErrorView
import com.example.chibbistest.ui.components.LoadingView
import com.example.chibbistest.ui.navigation.BottomNavigationScreens
import com.example.chibbistest.ui.viewmodels.hitslist.HitsListState
import com.example.chibbistest.ui.viewmodels.hitslist.HitsListViewModel
import com.example.chibbistest.ui.viewmodels.hitslist.HitsListViewModelFactory
import com.skydoves.landscapist.glide.GlideImage

object HitsListScreen {

    @Composable
    fun Screen() {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val viewModel: HitsListViewModel =
                viewModel(factory = HitsListViewModelFactory())

            AppBar.AppBar(
                title = stringResource(id = BottomNavigationScreens.Hits.resourceId),
                icon = BottomNavigationScreens.Hits.icon
            )

            var screenState: HitsListState by remember {
                mutableStateOf(
                    HitsListState.Loading
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
                viewModel.fetchHits()
            }

            when (screenState) {
                is HitsListState.Loaded -> {
                    ListView((screenState as HitsListState.Loaded))
                }
                is HitsListState.Loading -> {
                    LoadingView.LoadingView()
                }
                is HitsListState.Error -> {
                    ErrorView.ErrorView((screenState as HitsListState.Error).message)
                }
            }
        }
    }

    @Composable
    fun ListView(screenState: HitsListState.Loaded) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(UiConsts.screenPadding),
            modifier = Modifier
                .padding(horizontal = UiConsts.screenPadding)
                .fillMaxWidth()
        ) {
            itemsIndexed(screenState.hits) { index, hit ->
                if (index == 0) {
                    Spacer(modifier = Modifier.height(UiConsts.screenPadding))
                }
                HitsListItem(hit)
            }
        }
    }

    @Composable
    fun HitsListItem(hit: Hit) {
        Card(elevation = 10.dp) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(UiConsts.screenPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(UiConsts.bigListItemHeight),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(verticalArrangement = Arrangement.Center) {
                        GlideImage(
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(UiConsts.cardItemSmallImageSize)
                                .height(UiConsts.cardItemSmallImageSize)
                                .border(1.dp, Color.White, CircleShape),
                            imageModel = hit.restaurantLogoUrl,
                            contentScale = ContentScale.Fit,
                            placeHolder = Icons.Outlined.BrokenImage,
                            error = Icons.Outlined.BrokenImage,
                        )
                    }
                    Spacer(modifier = Modifier.width(UiConsts.screenPadding))
                    Column(verticalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = hit.productName,
                            fontSize = UiConsts.textLargeSize,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = hit.restaurantName,
                            style = TextStyle(
                                fontSize = UiConsts.textMediumSize,
                            )
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(UiConsts.cardItemBigImageSize),
                        imageModel = hit.productImageUrl,
                        contentScale = ContentScale.Fit,
                        placeHolder = Icons.Outlined.BrokenImage,
                        error = Icons.Outlined.BrokenImage,
                    )
                }
                Spacer(modifier = Modifier.height(UiConsts.screenPadding))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = hit.productDescriptionString,
                        style = TextStyle(
                            fontSize = UiConsts.textSmallSize,
                            color = ColorConsts.secondaryTextColor
                        )
                    )
                }
            }
        }
    }
}
