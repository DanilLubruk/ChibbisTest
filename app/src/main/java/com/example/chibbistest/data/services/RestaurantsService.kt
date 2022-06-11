package com.example.chibbistest.data.services

import com.example.chibbistest.data.models.Restaurant
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestaurantsService {
    @GET("restaurants")
    suspend fun getAllRestaurants(): Response<List<Restaurant>>

    companion object {
        private var service: RestaurantsService? = null

        fun getInstance(): RestaurantsService {
            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConsts.apiHttpAddress)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                service = retrofit.create(RestaurantsService::class.java)
            }

            return service!!;
        }
    }
}