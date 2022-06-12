package com.example.chibbistest.data.repositories

import com.example.chibbistest.data.services.RestaurantsService

class RestaurantsRepository constructor(private val restaurantsService: RestaurantsService) {
    suspend fun getAllRestaurants() = restaurantsService.getAllRestaurants()
}