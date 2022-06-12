package com.example.chibbistest.ui.viewmodels.reviewslist

import com.example.chibbistest.data.models.Hit
import com.example.chibbistest.data.models.Review
import com.example.chibbistest.ui.viewmodels.hitslist.HitsListState

sealed class ReviewsListState {
    data class Loaded(val reviews: List<Review>) : ReviewsListState()
    data class Error(val message: String) : ReviewsListState()
    object Loading : ReviewsListState()
}