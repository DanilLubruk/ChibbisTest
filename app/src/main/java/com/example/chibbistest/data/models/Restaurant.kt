package com.example.chibbistest.data.models

data class Restaurant(
    val Name: String,
    val Logo: String,
    val MinCost: Int,
    val DeliveryCost: Int,
    val DeliveryTime: Int,
    val PositiveReviews: Int,
    val ReviewsCount: Int,
    val Specializations: List<Specialization>
)