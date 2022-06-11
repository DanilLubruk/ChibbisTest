package com.example.chibbistest.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import com.example.chibbistest.R

object AppBar {
    @Composable
    fun AppBar(
        title: String,
        icon: ImageVector,
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    color = Color.White
                )
            },
            backgroundColor = colorResource(id = R.color.royalBlue),
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(icon, contentDescription = "", tint = Color.White)
                }
            },
        )
    }
}