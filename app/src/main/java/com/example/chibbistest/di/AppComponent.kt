package com.example.chibbistest.di

import com.example.chibbistest.ui.viewmodels.hitslist.HitsListViewModelFactory
import com.example.chibbistest.ui.viewmodels.restaurantslist.RestaurantsListViewModelFactory
import com.example.chibbistest.ui.viewmodels.reviewslist.ReviewsListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@AppScope
@Singleton
interface AppComponent {
    fun inject(restaurantsListViewModelFactory: RestaurantsListViewModelFactory)
    fun inject(hitsListViewModelFactory: HitsListViewModelFactory)
    fun inject(reviewsListViewModelFactory: ReviewsListViewModelFactory)
}