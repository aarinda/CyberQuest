package com.example.cyberquest.model

data class PlayerProgress(
    val completedLevels: Set<String> = emptySet(),
    val coins: Int = 0,
    val badges: Int = 0
)