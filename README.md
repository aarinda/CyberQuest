# CyberQuest

**CyberQuest** is a gamified cybersecurity awareness app built with Android Jetpack Compose and Room Database. It helps users learn about cybersecurity through interactive challenges and quizzes, while securely managing user accounts.

---

## Features

- **User Authentication:** Secure account creation and login using hashed and salted passwords.
- **Room Database:** Local persistence for user data.
- **Modern UI:** Built with Jetpack Compose for a responsive and engaging experience.
- **Navigation:** Seamless navigation between splash, authentication, main menu, and game screens.
- **Cybersecurity Content:** Gamified learning modules (expandable).

---

## Security

- **Passwords are never stored in plain text.**
- Passwords are hashed with a unique salt using PBKDF2 before being stored in the database.
- User authentication is performed by verifying the password hash.

---

## Project Structure

```
app/
 └── src/
      └── main/
           ├── java/com/example/cyberquest/
           │    ├── data/           # Room entities, DAO, repository
           │    ├── ui/             # Compose UI screens
           │    ├── util/           # Security utilities
           │    └── MainActivity.kt # App entry point
           └── res/                 # Resources (themes, drawables, etc.)
```

---

## Getting Started

1. **Clone the repository:**
    ```sh
    git clone https://github.com/aarinda/CyberQuest.git
    ```

2. **Open in Android Studio or VS Code.**

3. **Build and run on an Android device or emulator.**

---

## Dependencies

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

## How to Use

- **Sign Up:** Create a new account with a unique email and password.
- **Login:** Access your account securely.
- **Explore:** Navigate through the main menu and start learning about cybersecurity.

---

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## License

This project is licensed under the MIT License.

---

**CyberQuest**