package com.hotmail.or_dvir.arisquiz.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun FullScreenProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { /*prevents clicks from going through*/ },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
