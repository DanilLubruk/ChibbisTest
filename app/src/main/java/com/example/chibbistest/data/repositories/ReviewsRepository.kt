package com.example.chibbistest.data.repositories

import com.example.chibbistest.data.services.ReviewsService

class ReviewsRepository constructor(private val reviewsService: ReviewsService) {
    suspend fun getAllReviews() = reviewsService.getAllReviews()
}