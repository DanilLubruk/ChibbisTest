package com.example.chibbistest.data.models

import com.example.chibbistest.utils.TimeUtils
import java.util.*


data class Review(
    //нашёл на стеке инфу, что именя JSON полей начинающихся с i
    //всегда будут false
    //я просто реверсну здесь булевую переменную, что бы были плюсики везде
    //т.к. в данных везде позитивные отзывы
    //просто что бы вы понял, почему так я сделал
    //ссылка на статью про ошибку https://stackoverflow.com/questions/40190697/android-retrofit-always-returns-false-value-for-the-type-boolean-while-fetching
    private val IsPosivite: Boolean,
    private val Message: String,
    private val DateAdded: Date,
    private val UserFIO: String,
    private val RestaurantName: String
) {
    val isPositive
        get() = !IsPosivite

    val message
        get() = Message.trim()

    val dateString
        get() = "${TimeUtils.dateToLocaleStr(DateAdded)} ${TimeUtils.dateTo24HoursTime(DateAdded)}"

    val userName
        get() = UserFIO.trim()

    val restaurantName
        get() = RestaurantName.trim()
}