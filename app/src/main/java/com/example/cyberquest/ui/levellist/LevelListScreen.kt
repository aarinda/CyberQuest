package com.example.cyberquest.ui.levellist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyberquest.NavRoutes
import com.example.cyberquest.model.LevelRepository

@Composable
fun LevelListScreen(navController: NavController, modifier: Modifier = Modifier) {
    val levels = LevelRepository.levels
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Select a Level", style = MaterialTheme.typography.headlineMedium)
        levels.forEach { level ->
            Button(onClick = { navController.navigate(NavRoutes.puzzleRoute(level.id)) }) {
                Text("${level.title}")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Main Menu")
        }
    }
}