package com.example.chibbistest.ui.viewmodels.restaurantslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chibbistest.data.models.Restaurant
import com.example.chibbistest.data.repositories.RestaurantsRepository
import com.example.chibbistest.ui.viewmodels.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RestaurantsListViewModel @Inject constructor(private val restaurantsRepository: RestaurantsRepository) :
    BaseViewModel() {
    private val responseList =
        MutableStateFlow<List<Restaurant>>(listOf())

    private val mutableListState =
        MutableStateFlow<RestaurantsListState>(RestaurantsListState.Loading)
    val listState: StateFlow<RestaurantsListState> = mutableListState

    fun fetchRestaurants() {
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = restaurantsRepository.getAllRestaurants()
                if (response.isSuccessful) {
                    val list: List<Restaurant> = response.body()!!
                    responseList.emit(list)
                    mutableListState.emit(RestaurantsListState.Loaded(sortList(responseList.value)))
                } else {
                    mutableListState.emit(RestaurantsListState.Error(response.message()))
                }
            } catch (exc: Throwable) {
                exc.printStackTrace()
                mutableListState.emit(RestaurantsListState.Error(exc.localizedMessage!!))
            }
        })
    }

    fun onSearchTextChanged(searchText: String) {
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            val list: List<Restaurant> = responseList.value
            mutableListState.emit(RestaurantsListState.Loaded(sortList(list.filter {
                it.name.lowercase(Locale.getDefault()).contains(
                    searchText.lowercase(Locale.getDefault())
                )
            })))
        })
    }

    private fun sortList(list: List<Restaurant>): List<Restaurant> =
        list.sortedByDescending { it.positiveReviews }
}