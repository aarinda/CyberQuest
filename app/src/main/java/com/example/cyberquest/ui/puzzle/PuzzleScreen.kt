package com.example.cyberquest.ui.puzzle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyberquest.data.ProgressManager
import com.example.cyberquest.model.LevelRepository
import com.example.cyberquest.model.PuzzleType
import kotlinx.coroutines.launch

@Composable
fun PuzzleScreen(navController: NavController, levelId: String?, modifier: Modifier = Modifier) {
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showFailureDialog by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }
    val level = levelId?.let { LevelRepository.getLevelById(it) }

    val context = LocalContext.current
    val progressManager = remember { ProgressManager(context) }
    val coroutineScope = rememberCoroutineScope()

    // Use per-level rewards if available, else default
    val coinsEarned = level?.coins ?: 10
    val badgesEarned = level?.badges ?: if (level?.type == PuzzleType.PASSWORD_CRACKER) 1 else 0

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (level != null) {
                when (level.type) {
                    PuzzleType.PASSWORD_CRACKER -> PasswordCrackerPuzzle(
                        correctPassword = level.solution,
                        maxTries = 5,
                        onSolved = {
                            coroutineScope.launch {
                                progressManager.markLevelCompleted(level.id, coinsEarned, badgesEarned)
                            }
                            showConfetti = true
                            showSuccessDialog = true
                        },
                        onFailed = { showFailureDialog = true }
                    )
                    PuzzleType.CAESAR_CIPHER -> {
                        val extraParts = level.extra?.split("|") ?: listOf()
                        val shift = extraParts.getOrNull(0)?.toIntOrNull() ?: 3
                        val encodedText = extraParts.getOrNull(1) ?: ""
                        CaesarCipherPuzzle(
                            encodedText = encodedText,
                            shift = shift,
                            solution = level.solution,
                            onSolved = {
                                coroutineScope.launch {
                                    progressManager.markLevelCompleted(level.id, coinsEarned, badgesEarned)
                                }
                                showConfetti = true
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

        // --- Animated Confetti/Reward Icon ---
        AnimatedVisibility(
            visible = showConfetti,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Trophy",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "You earned $coinsEarned coins${if (badgesEarned > 0) " and $badgesEarned badge(s)" else ""}!",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                showConfetti = false
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Trophy",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
            },
            title = { Text("Congratulations!") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("You solved the puzzle!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Coins: $coinsEarned")
                        if (badgesEarned > 0) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Badge",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Badges: $badgesEarned")
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSuccessDialog = false
                    showConfetti = false
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