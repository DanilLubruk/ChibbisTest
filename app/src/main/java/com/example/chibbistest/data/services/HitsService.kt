package com.example.chibbistest.data.services

import com.example.chibbistest.data.models.Hit
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HitsService {
    @GET("hits")
    suspend fun getAllHits(): Response<List<Hit>>

    companion object {
        private var service: HitsService? = null

        fun getInstance(): HitsService {
            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConsts.apiHttpAddress)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                service = retrofit.create(HitsService::class.java)
            }

            return service!!
        }
    }
}