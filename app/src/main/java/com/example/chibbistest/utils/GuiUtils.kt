package com.example.chibbistest.utils

import android.content.Context
import android.widget.Toast

object GuiUtils {
    fun showShortToast(context: Context, text: String) =
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()

    fun showLongToast(context: Context, text: String) =
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()

}