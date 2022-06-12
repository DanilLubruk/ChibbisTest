package com.example.chibbistest.data.models

import com.example.chibbistest.utils.ResUtils
import com.example.chibbistest.R

data class Hit(
    private val ProductName: String,
    private val ProductImage: String,
    private val ProductDescription: String,
    private val RestaurantId: Int,
    private val RestaurantName: String,
    private val RestaurantLogo: String,
) {
    val productName
        get() = ProductName.trim()

    val productImageUrl
        get() = ProductImage

    val productDescriptionString
        get() = "${ResUtils.getString(R.string.caption_recipe)}: ${ProductDescription.trim()}"

    val restaurantName
        get() = RestaurantName.trim()

    val restaurantLogoUrl
        get() = RestaurantLogo
}