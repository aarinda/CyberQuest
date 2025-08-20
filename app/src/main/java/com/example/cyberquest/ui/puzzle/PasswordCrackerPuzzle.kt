package com.example.cyberquest.ui.puzzle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PasswordCrackerPuzzle(
    correctPassword: String = "357",
    maxTries: Int = 5,
    onSolved: () -> Unit = {},
    onFailed: () -> Unit = {}
) {
    var input by remember { mutableStateOf("") }
    var tries by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }
    var solved by remember { mutableStateOf(false) }
    var failed by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Text("Password Cracker", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Guess the 3-digit code. You have ${maxTries - tries} tries left.")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = input,
            onValueChange = { if (it.length <= 3) input = it },
            label = { Text("Enter code") },
            enabled = !solved && !failed
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                tries++
                if (input == correctPassword) {
                    message = "Correct! You cracked the password."
                    solved = true
                    onSolved()
                } else if (tries >= maxTries) {
                    message = "Out of tries! The code was $correctPassword."
                    failed = true
                    onFailed()
                } else {
                    message = "Incorrect. Try again."
                }
                input = ""
            },
            enabled = !solved && !failed && input.length == 3
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(message)
    }
}