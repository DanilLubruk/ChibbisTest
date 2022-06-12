package com.example.chibbistest.data.services

import com.example.chibbistest.data.models.Restaurant
import com.example.chibbistest.data.models.Review
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ReviewsService {
    @GET("reviews")
    suspend fun getAllReviews(): Response<List<Review>>

    companion object {
        private var service: ReviewsService? = null

        fun getInstance(): ReviewsService {
            if (service == null) {
                val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
                val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConsts.apiHttpAddress)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                    .build()
                service = retrofit.create(ReviewsService::class.java)
            }

            return service!!
        }
    }
}