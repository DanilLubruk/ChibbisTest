package com.example.chibbistest.ui.viewmodels.restaurantslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chibbistest.ChibbisTestApp
import javax.inject.Inject

class RestaurantsListViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var viewModel: RestaurantsListViewModel

    init {
        ChibbisTestApp.instance.appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
}