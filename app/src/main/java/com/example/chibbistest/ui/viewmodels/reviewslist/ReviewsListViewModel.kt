package com.example.chibbistest.ui.viewmodels.reviewslist

import androidx.lifecycle.viewModelScope
import com.example.chibbistest.data.repositories.HitsRepository
import com.example.chibbistest.data.repositories.ReviewsRepository
import com.example.chibbistest.ui.viewmodels.BaseViewModel
import com.example.chibbistest.ui.viewmodels.hitslist.HitsListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReviewsListViewModel @Inject constructor(private val reviewsRepository: ReviewsRepository) :
    BaseViewModel() {

    private val mutableListState =
        MutableStateFlow<ReviewsListState>(ReviewsListState.Loading)
    val listState: StateFlow<ReviewsListState> = mutableListState

    fun fetchReviews() {
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = reviewsRepository.getAllReviews()
                if (response.isSuccessful) {
                    mutableListState.emit(ReviewsListState.Loaded(response.body()!!))
                } else {
                    mutableListState.emit(ReviewsListState.Error(response.message()))
                }
            } catch (exc: Throwable) {
                exc.printStackTrace()
                mutableListState.emit(ReviewsListState.Error(exc.localizedMessage!!))
            }
        })
    }
}