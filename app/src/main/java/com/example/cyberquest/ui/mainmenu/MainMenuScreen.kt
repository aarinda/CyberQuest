package com.example.cyberquest.ui.mainmenu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyberquest.data.ProgressManager

@Composable
fun MainMenuScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val progressManager = remember { ProgressManager(context) }
    val progress = progressManager.progressFlow.collectAsState(initial = com.example.cyberquest.model.PlayerProgress())

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("CyberQuest", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Levels completed: ${progress.value.completedLevels.size}")
        Text("Coins: ${progress.value.coins}")
        Text("Badges: ${progress.value.badges}")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigate("level_list") }) {
            Text("Start Game")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("how_to_play") }) {
            Text("How to Play")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("settings") }) {
            Text("Settings")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("about") }) {
            Text("About")
        }
    }
}