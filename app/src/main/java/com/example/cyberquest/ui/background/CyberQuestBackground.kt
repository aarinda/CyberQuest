package com.example.cyberquest.ui.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun CyberQuestBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0B1D26), // Deep Night
                        Color(0xFF00FFB4), // Aurora Green
                        Color(0xFF00FFC6), // Aurora Teal
                        Color(0xFF00CFFF), // Aurora Blue
                        Color(0xFF7F5FFF)  // Aurora Purple (optional, for a hint)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 2000f)
                )
            )
    ) {
        content()
    }
}