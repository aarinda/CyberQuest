package com.example.cyberquest.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AuthScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to CyberQuest!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("login") }, modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("sign_up") }, modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Sign Up")
            }
        }
    }
}
