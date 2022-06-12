package com.example.chibbistest.ui.viewmodels.hitslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chibbistest.ChibbisTestApp
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListViewModel
import javax.inject.Inject

class HitsListViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var viewModel: HitsListViewModel

    init {
        ChibbisTestApp.instance.appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
}