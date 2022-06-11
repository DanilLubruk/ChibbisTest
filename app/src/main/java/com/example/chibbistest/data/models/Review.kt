package com.example.chibbistest.data.models

import java.util.*

data class Review(
    val isPosivite: Boolean,
    val message: String,
    val dateAdded: Date,
    val userFio: String,
    val restaurantName: String
)