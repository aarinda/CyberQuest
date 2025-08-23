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
import com.example.cyberquest.ui.theme.AuroraBlue
import com.example.cyberquest.ui.theme.AuroraGreen
import com.example.cyberquest.ui.theme.AuroraTeal

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
        Text("CyberQuest", style = MaterialTheme.typography.headlineLarge, color = AuroraGreen)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Levels completed: ${progress.value.completedLevels.size}", color = AuroraTeal)
        Text("Coins: ${progress.value.coins}", color = AuroraTeal)
        Text("Badges: ${progress.value.badges}", color = AuroraTeal)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("level_list") },
            colors = ButtonDefaults.buttonColors(containerColor = AuroraGreen),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Start Game")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("how_to_play") },
            colors = ButtonDefaults.buttonColors(containerColor = AuroraBlue),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("How to Play")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("settings") },
            colors = ButtonDefaults.buttonColors(containerColor = AuroraTeal),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Settings")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("about") },
            colors = ButtonDefaults.buttonColors(containerColor = AuroraBlue),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("About")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.navigate("auth") {
                    popUpTo("main_menu") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = AuroraTeal),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Logout")
        }
    }
}