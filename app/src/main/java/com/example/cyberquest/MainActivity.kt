package com.example.cyberquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cyberquest.ui.auth.AuthScreen
import com.example.cyberquest.ui.auth.LoginScreen
import com.example.cyberquest.ui.auth.SignUpScreen
import com.example.cyberquest.ui.mainmenu.MainMenuScreen
import com.example.cyberquest.ui.splash.SplashScreen
import com.example.cyberquest.ui.theme.CyberQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberQuestTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CyberQuestApp()
                }
            }
        }
    }
}

@Composable
fun CyberQuestApp(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("auth") { AuthScreen(navController) }
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate("main_menu") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        composable("sign_up") {
            SignUpScreen(
                navController = navController,
                onSignUpSuccess = {
                    navController.navigate("main_menu") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        composable("main_menu") { MainMenuScreen(navController = navController) }
        // Add other routes as needed
    }
}
