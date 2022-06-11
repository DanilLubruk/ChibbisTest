package com.example.chibbistest.ui.viewmodels

import com.example.chibbistest.data.models.Restaurant

sealed class RestaurantsListState {
    data class Loaded(val restaurants: List<Restaurant>) : RestaurantsListState()
    data class Error(val message: String) : RestaurantsListState()
    object Loading : RestaurantsListState()
}
