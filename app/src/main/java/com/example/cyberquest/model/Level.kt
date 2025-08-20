package com.example.cyberquest.model

enum class PuzzleType {
    PASSWORD_CRACKER,
    CAESAR_CIPHER,
    // Add more types as you implement them
}

data class Level(
    val id: String,
    val title: String,
    val description: String,
    val type: PuzzleType,
    val solution: String,
    val extra: String? = null // For things like cipher shift, encoded text, etc.
)