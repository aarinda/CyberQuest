package com.example.cyberquest.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF0F2027), Color.Black)
                )
            )
    ) {
        // TODO: Add faint glowing circuit lines as overlay

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated 3D Lock (placeholder icon for now)
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "3D Lock",
                tint = Color.Cyan,
                modifier = Modifier
                    .size(96.dp)
                    .shadow(24.dp, shape = RoundedCornerShape(48.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "CyberQuest",
                color = Color(0xFF00C9FF),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.shadow(8.dp),
                textAlign = TextAlign.Center
            )
            Text(
                "Gamified Cybersecurity Awareness by PrysmSecure",
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    // TODO: Replace with real authentication logic
                    if (email.isNotBlank() && password.isNotBlank()) {
                        onLoginSuccess()
                    } else {
                        error = "Please enter email and password."
                    }
                },
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C9FF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(32.dp))
            ) {
                Text("Login", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = { navController.navigate("sign_up") }
            ) {
                Text("Don't have an account? Sign Up", color = Color.Cyan)
            }
            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { navController.navigate("auth") }) {
                Text("Back")
            }
        }
    }
}