package com.example.chibbistest.ui.viewmodels.hitslist

import com.example.chibbistest.data.models.Hit
import com.example.chibbistest.data.models.Restaurant
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListState

sealed class HitsListState {
    data class Loaded(val hits: List<Hit>) : HitsListState()
    data class Error(val message: String) : HitsListState()
    object Loading : HitsListState()
}