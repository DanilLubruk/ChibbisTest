package com.example.chibbistest.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chibbistest.ui.UiConsts

object LoadingView {
    @Composable
    fun LoadingView() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(UiConsts.circularProgressBarSize)
                    .width(UiConsts.circularProgressBarSize),
                strokeWidth = 2.dp,
            )
        }
    }
}