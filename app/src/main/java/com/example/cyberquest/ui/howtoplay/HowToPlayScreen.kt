package com.example.cyberquest.ui.howtoplay

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HowToPlayScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("How to Play", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Solve cybersecurity puzzles to progress through levels. Each level has instructions. Good luck!")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}