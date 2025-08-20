package com.example.cyberquest.model

object LevelRepository {
    val levels = listOf(
        Level(
            id = "1",
            title = "Password Cracker",
            description = "Guess the 3-digit code. You have 5 tries!",
            type = PuzzleType.PASSWORD_CRACKER,
            solution = "357"
        ),
        Level(
            id = "2",
            title = "Caesar Cipher",
            description = "Decode: 'Fdhvdu' (shift 3)",
            type = PuzzleType.CAESAR_CIPHER,
            solution = "Caesar",
            extra = "3|Fdhvdu" // shift|encodedText
        )
        // Add more levels as needed
    )

    fun getLevelById(id: String): Level? = levels.find { it.id == id }
}