package com.example.cyberquest.dataimport com.example.cyberquest.util.SecurityUtils

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(username: String, password: String): Boolean {
        val salt = SecurityUtils.generateSalt()
        val hash = SecurityUtils.hashPassword(password, salt)
        return try {
            userDao.insertUser(User(username, hash, salt))
            true
        } catch (e: Exception) {
            false // Username already exists or other error
        }
    }

    suspend fun loginUser(username: String, password: String): Boolean {
        val user = userDao.getUserByUsername(username) ?: return false
        return SecurityUtils.verifyPassword(password, user.salt, user.passwordHash)
    }
}

