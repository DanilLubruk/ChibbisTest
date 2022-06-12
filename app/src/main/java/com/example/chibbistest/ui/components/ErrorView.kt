package com.example.chibbistest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.chibbistest.R
import com.example.chibbistest.ui.UiConsts
import com.example.chibbistest.ui.viewmodels.RestaurantsListState
import com.example.chibbistest.utils.GuiUtils

object ErrorView {
    @Composable
    fun ErrorView(message: String) {
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.message_error_obtaining_data),
                fontSize = UiConsts.textMediumSize
            )
            GuiUtils.showShortToast(
                context,
                message
            )
        }
    }
}