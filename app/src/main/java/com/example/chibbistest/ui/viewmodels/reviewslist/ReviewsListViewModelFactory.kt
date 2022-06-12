package com.example.chibbistest.ui.viewmodels.reviewslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chibbistest.ChibbisTestApp
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListViewModel
import javax.inject.Inject

class ReviewsListViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var viewModel: ReviewsListViewModel

    init {
        ChibbisTestApp.instance.appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
}