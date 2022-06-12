package com.example.chibbistest.ui.listscreens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chibbistest.R
import com.example.chibbistest.data.models.Hit
import com.example.chibbistest.data.models.Review
import com.example.chibbistest.ui.ColorConsts
import com.example.chibbistest.ui.UiConsts
import com.example.chibbistest.ui.components.AppBar
import com.example.chibbistest.ui.components.ErrorView
import com.example.chibbistest.ui.components.LoadingView
import com.example.chibbistest.ui.navigation.BottomNavigationScreens
import com.example.chibbistest.ui.viewmodels.reviewslist.ReviewsListState
import com.example.chibbistest.ui.viewmodels.reviewslist.ReviewsListViewModel
import com.example.chibbistest.ui.viewmodels.reviewslist.ReviewsListViewModelFactory
import com.skydoves.landscapist.glide.GlideImage

object ReviewsListScreen {

    @Composable
    fun Screen() {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            val viewModel: ReviewsListViewModel =
                viewModel(factory = ReviewsListViewModelFactory())

            AppBar.AppBar(
                title = stringResource(id = BottomNavigationScreens.Reviews.resourceId),
                icon = BottomNavigationScreens.Reviews.icon
            )

            var screenState: ReviewsListState by remember {
                mutableStateOf(
                    ReviewsListState.Loading
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
                viewModel.fetchReviews()
            }

            when (screenState) {
                is ReviewsListState.Loaded -> {
                    ListView((screenState as ReviewsListState.Loaded))
                }
                is ReviewsListState.Loading -> {
                    LoadingView.LoadingView()
                }
                is ReviewsListState.Error -> {
                    ErrorView.ErrorView((screenState as ReviewsListState.Error).message)
                }
            }
        }
    }

    @Composable
    fun ListView(screenState: ReviewsListState.Loaded) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(UiConsts.screenPadding),
            modifier = Modifier
                .padding(horizontal = UiConsts.screenPadding)
                .fillMaxWidth()
        ) {
            itemsIndexed(screenState.reviews) { index, review ->
                if (index == 0) {
                    Spacer(modifier = Modifier.height(UiConsts.screenPadding))
                }
                ReviewListItem(review)
            }
        }
    }

    @Composable
    fun ReviewListItem(review: Review) {
        Card(elevation = 10.dp) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(UiConsts.screenPadding)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = review.userName,
                            style = TextStyle(
                                fontSize = UiConsts.textLargeSize,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(UiConsts.listItemMediumMargin))
                        Text(
                            text = review.dateString,
                            style = TextStyle(
                                fontSize = UiConsts.textSmallSize,
                                color = ColorConsts.secondaryTextColor
                            )
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .height(UiConsts.mediumImageSize)
                            .width(UiConsts.mediumImageSize),
                        imageVector = if (review.isPositive) Icons.Filled.ThumbUp else Icons.Filled.ThumbDown,
                        contentDescription = "",
                        tint = if (review.isPositive) colorResource(id = R.color.deepGreen) else Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = review.message,
                        style = TextStyle(
                            fontSize = UiConsts.textMediumSize,
                        )
                    )
                }
                Spacer(modifier = Modifier.height(UiConsts.listItemMediumMargin))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${stringResource(id = R.string.caption_about)} ${review.restaurantName}",
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
