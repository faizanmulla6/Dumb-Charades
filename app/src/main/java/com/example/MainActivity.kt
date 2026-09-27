package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.model.Screen
import com.example.ui.screens.ActiveRoundScreen
import com.example.ui.screens.GameOverScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MovieLibraryScreen
import com.example.ui.screens.RoundSummaryScreen
import com.example.ui.screens.RulesGuideScreen
import com.example.ui.screens.SetupGameScreen
import com.example.ui.screens.TurnPassScreen
import com.example.ui.theme.DarkIndigo
import com.example.ui.theme.DumbCharadesTheme
import com.example.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DumbCharadesTheme {
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = DarkIndigo
                ) { innerPadding ->
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "screen_transition",
                        modifier = Modifier.padding(innerPadding)
                    ) { targetScreen ->
                        when (targetScreen) {
                            is Screen.Home -> HomeScreen(viewModel = viewModel)
                            is Screen.GameSetup -> SetupGameScreen(viewModel = viewModel)
                            is Screen.TurnPass -> TurnPassScreen(viewModel = viewModel)
                            is Screen.ActiveRound -> ActiveRoundScreen(viewModel = viewModel)
                            is Screen.RoundSummary -> RoundSummaryScreen(viewModel = viewModel)
                            is Screen.GameOver -> GameOverScreen(viewModel = viewModel)
                            is Screen.MovieLibrary -> MovieLibraryScreen(viewModel = viewModel)
                            is Screen.GameHistory -> HistoryScreen(viewModel = viewModel)
                            is Screen.RulesGuide -> RulesGuideScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
