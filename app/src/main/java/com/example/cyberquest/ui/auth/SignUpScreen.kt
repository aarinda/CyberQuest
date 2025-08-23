package com.example.cyberquest.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyberquest.ui.theme.AuroraGreen
import com.example.cyberquest.ui.theme.AuroraTeal

@Composable
fun SignUpScreen(
    navController: NavController,
    onSignUpSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign Up", style = MaterialTheme.typography.headlineMedium, color = AuroraGreen)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = AuroraTeal) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = AuroraTeal) },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = AuroraTeal) },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                // TODO: Replace with real sign up logic
                if (email.isNotBlank() && password == confirmPassword && password.isNotBlank()) {
                    onSignUpSuccess()
                } else {
                    error = "Please check your input."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = AuroraGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }
        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Login", color = AuroraTeal)
        }
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Back", color = AuroraTeal)
        }
    }
}