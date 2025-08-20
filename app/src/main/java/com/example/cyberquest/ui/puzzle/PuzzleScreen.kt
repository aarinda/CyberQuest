package com.example.cyberquest.ui.puzzle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyberquest.model.LevelRepository
import com.example.cyberquest.model.PuzzleType
import androidx.compose.ui.platform.LocalContext
import com.example.cyberquest.data.ProgressManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun PuzzleScreen(navController: NavController, levelId: String?, modifier: Modifier = Modifier) {
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showFailureDialog by remember { mutableStateOf(false) }
    val level = levelId?.let { com.example.cyberquest.model.LevelRepository.getLevelById(it) }

    val context = LocalContext.current
    val progressManager = remember { ProgressManager(context) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (level != null) {
            when (level.type) {
                com.example.cyberquest.model.PuzzleType.PASSWORD_CRACKER -> PasswordCrackerPuzzle(
                    correctPassword = level.solution,
                    maxTries = 5,
                    onSolved = {
                        coroutineScope.launch {
                            progressManager.markLevelCompleted(level.id, coinsEarned = 10)
                        }
                        showSuccessDialog = true
                    },
                    onFailed = { showFailureDialog = true }
                )
                com.example.cyberquest.model.PuzzleType.CAESAR_CIPHER -> {
                    val (shift, encodedText) = level.extra?.split("|")?.let {
                        it[0].toIntOrNull() ?: 3 to (it.getOrNull(1) ?: "")
                    } ?: (3 to "")
                    CaesarCipherPuzzle(
                        encodedText = encodedText,
                        shift = shift,
                        solution = level.solution,
                        onSolved = {
                            coroutineScope.launch {
                                progressManager.markLevelCompleted(level.id, coinsEarned = 10)
                            }
                            showSuccessDialog = true
                        },
                        onFailed = { showFailureDialog = true }
                    )
                }
                // Add more puzzle types here
            }
        } else {
            Text("Puzzle not found.", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Level List")
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Congratulations!") },
            text = { Text("You solved the puzzle!") },
            confirmButton = {
                Button(onClick = {
                    showSuccessDialog = false
                    navController.popBackStack()
                }) {
                    Text("OK")
                }
            }
        )
    }

    if (showFailureDialog) {
        AlertDialog(
            onDismissRequest = { showFailureDialog = false },
            title = { Text("Try Again") },
            text = { Text("You failed to solve the puzzle.") },
            confirmButton = {
                Button(onClick = {
                    showFailureDialog = false
                    navController.popBackStack()
                }) {
                    Text("Back")
                }
            }
        )
    }
}

// --- PasswordCrackerPuzzle remains as previously implemented ---

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

// --- CaesarCipherPuzzle implementation ---

@Composable
fun CaesarCipherPuzzle(
    encodedText: String,
    shift: Int,
    solution: String,
    onSolved: () -> Unit = {},
    onFailed: () -> Unit = {}
) {
    var input by remember { mutableStateOf("") }
    var tries by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }
    var solved by remember { mutableStateOf(false) }
    var failed by remember { mutableStateOf(false) }
    val maxTries = 3

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Text("Cipher Challenge", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Decode: \"$encodedText\" (shift $shift)")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Decoded text") },
            enabled = !solved && !failed
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                tries++
                if (input.trim().equals(solution, ignoreCase = true)) {
                    message = "Correct! Well done."
                    solved = true
                    onSolved()
                } else if (tries >= maxTries) {
                    message = "Out of tries! The answer was \"$solution\"."
                    failed = true
                    onFailed()
                } else {
                    message = "Incorrect. Try again."
                }
                input = ""
            },
            enabled = !solved && !failed && input.isNotBlank()
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(message)
    }
}