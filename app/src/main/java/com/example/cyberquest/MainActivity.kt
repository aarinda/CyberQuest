package com.example.cyberquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cyberquest.ui.mainmenu.MainMenuScreen
import com.example.cyberquest.ui.levellist.LevelListScreen
import com.example.cyberquest.ui.puzzle.PuzzleScreen
import com.example.cyberquest.ui.howtoplay.HowToPlayScreen
import com.example.cyberquest.ui.settings.SettingsScreen
import com.example.cyberquest.ui.about.AboutScreen
import com.example.cyberquest.ui.theme.CyberQuestTheme

// Define route constants for better maintainability
object NavRoutes {
    const val MAIN_MENU = "main_menu"
    const val LEVEL_LIST = "level_list"
    const val PUZZLE_PREFIX = "puzzle" // Prefix for puzzle routes
    const val HOW_TO_PLAY = "how_to_play"
    const val SETTINGS = "settings"
    const val ABOUT = "about"

    // Helper function to create a route for a puzzle with its ID
    fun puzzleRoute(levelId: String) = "$PUZZLE_PREFIX/$levelId"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberQuestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CyberQuestApp()
                }
            }
        }
    }
}

@Composable
fun CyberQuestApp(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = NavRoutes.MAIN_MENU) {
        composable(NavRoutes.MAIN_MENU) {
            MainMenuScreen(navController = navController)
        }
        composable(NavRoutes.LEVEL_LIST) {
            LevelListScreen(navController = navController)
        }
        composable("${NavRoutes.PUZZLE_PREFIX}/{levelId}") { backStackEntry ->
            val levelId = backStackEntry.arguments?.getString("levelId")
            PuzzleScreen(navController = navController, levelId = levelId)
        }
        composable(NavRoutes.HOW_TO_PLAY) {
            HowToPlayScreen(navController = navController)
        }
        composable(NavRoutes.SETTINGS) {
            SettingsScreen(navController = navController)
        }
        composable(NavRoutes.ABOUT) {
            AboutScreen(navController = navController)
        }
    }
}
