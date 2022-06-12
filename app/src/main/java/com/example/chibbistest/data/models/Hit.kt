package com.example.chibbistest.data.models

data class Hit(
    private val ProductName: String,
    private val ProductImage: String,
    private val ProductDescription: String,
    private val RestaurantId: Int,
    private val RestaurantName: String,
    private val RestaurantLogo: String,
) {
    val productName
        get() = ProductName

    val productImageUrl
        get() = ProductImage

    val productDescription
        get() = ProductDescription

    val restaurantName
        get() = RestaurantName

    val restaurantLogoUrl
        get() = RestaurantLogo
}