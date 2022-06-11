package com.example.chibbistest.utils

import android.graphics.drawable.Drawable
import com.example.chibbistest.ChibbisTestApp

object ResUtils {
    fun getString(id: Int) =
        ChibbisTestApp.instance.resources.getString(id)
}