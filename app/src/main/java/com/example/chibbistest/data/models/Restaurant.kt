package com.example.chibbistest.data.models

import com.example.chibbistest.data.DataConsts
import com.example.chibbistest.utils.ResUtils
import com.example.chibbistest.R

data class Restaurant(
    private val Name: String,
    private val Logo: String,
    private val MinCost: Int,
    private val DeliveryCost: Int,
    private val DeliveryTime: Int,
    private val PositiveReviews: Int,
    private val ReviewsCount: Int,
    private val Specializations: List<Specialization>
) {
    val name
        get() = Name

    val logoUrl
        get() = Logo

    val minCostString
        get() = "${ResUtils.getString(R.string.caption_from)} $MinCost${DataConsts.currencySign}"

    val deliveryCostString
        get() =
            if (DeliveryCost > 0)
                "$DeliveryCost${DataConsts.currencySign}"
            else
                ResUtils.getString(R.string.caption_free)

    val deliveryTimeString
        get() = "$DeliveryTime ${ResUtils.getString(R.string.caption_minutes)}"

    val positiveReviewsString
        get() = "$PositiveReviews %"

    val positiveReviews
        get() = PositiveReviews

    val reviewsCount
        get() = "$ReviewsCount ${ResUtils.getString(R.string.caption_reviews)}"

    val specializationsString: String
        get() {
            val stringBuilder = StringBuilder()
            Specializations.forEachIndexed { index, specialization ->
                stringBuilder.append(specialization.Name)
                if (index != Specializations.size - 1) {
                    stringBuilder.append(" | ")
                }
            }

            return stringBuilder.toString()
        }
}