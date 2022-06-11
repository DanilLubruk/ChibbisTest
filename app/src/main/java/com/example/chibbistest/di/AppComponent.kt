package com.example.chibbistest.di

import com.example.chibbistest.ui.viewmodels.RestaurantsListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@AppScope
@Singleton
interface AppComponent {
    fun inject(restaurantsListViewModelFactory: RestaurantsListViewModelFactory)
}