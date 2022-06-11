package com.example.chibbistest.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chibbistest.data.repositories.RestaurantsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestaurantsListViewModel @Inject constructor(private val restaurantsRepository: RestaurantsRepository) :
    ViewModel() {

    var job: Job? = null

    private val mutableListState =
        MutableStateFlow<RestaurantsListState>(RestaurantsListState.Loading)

    val listState: StateFlow<RestaurantsListState> = mutableListState

    fun fetchRestaurants() {
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = restaurantsRepository.getAllRestaurants()
                if (response.isSuccessful) {
                    mutableListState.emit(RestaurantsListState.Loaded(response.body()!!))
                } else {
                    mutableListState.emit(RestaurantsListState.Error(response.message()))
                }
            } catch (exc: Throwable) {
                mutableListState.emit(RestaurantsListState.Error(exc.localizedMessage!!))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}