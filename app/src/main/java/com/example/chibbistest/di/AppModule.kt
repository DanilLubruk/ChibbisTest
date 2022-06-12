package com.example.chibbistest.di

import com.example.chibbistest.data.repositories.HitsRepository
import com.example.chibbistest.data.repositories.RestaurantsRepository
import com.example.chibbistest.data.repositories.ReviewsRepository
import com.example.chibbistest.data.services.HitsService
import com.example.chibbistest.data.services.RestaurantsService
import com.example.chibbistest.data.services.ReviewsService
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @AppScope
    @Provides
    fun provideRestaurantsService(): RestaurantsService = RestaurantsService.getInstance()

    @AppScope
    @Provides
    fun provideRestaurantsRepository(restaurantsService: RestaurantsService): RestaurantsRepository =
        RestaurantsRepository(restaurantsService)

    @AppScope
    @Provides
    fun provideHitsService(): HitsService = HitsService.getInstance()

    @AppScope
    @Provides
    fun provideHitsRepository(hitsService: HitsService): HitsRepository =
        HitsRepository(hitsService)

    @AppScope
    @Provides
    fun provideReviewsService(): ReviewsService = ReviewsService.getInstance()

    @AppScope
    @Provides
    fun provideReviewsRepository(reviewsService: ReviewsService): ReviewsRepository =
        ReviewsRepository(reviewsService)
}