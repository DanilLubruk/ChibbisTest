package com.example.chibbistest.ui.viewmodels.hitslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chibbistest.data.models.Restaurant
import com.example.chibbistest.data.repositories.HitsRepository
import com.example.chibbistest.data.repositories.RestaurantsRepository
import com.example.chibbistest.ui.viewmodels.BaseViewModel
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HitsListViewModel @Inject constructor(private val hitsRepository: HitsRepository) :
    BaseViewModel() {

    private val mutableListState =
        MutableStateFlow<HitsListState>(HitsListState.Loading)
    val listState: StateFlow<HitsListState> = mutableListState

    fun fetchHits() {
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = hitsRepository.getAllHits()
                if (response.isSuccessful) {
                    mutableListState.emit(HitsListState.Loaded(response.body()!!))
                } else {
                    mutableListState.emit(HitsListState.Error(response.message()))
                }
            } catch (exc: Throwable) {
                exc.printStackTrace()
                mutableListState.emit(HitsListState.Error(exc.localizedMessage!!))
            }
        })
    }
}