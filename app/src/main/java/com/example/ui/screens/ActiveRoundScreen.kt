package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ActorHintCard
import com.example.ui.components.CircularTimerRing
import com.example.ui.components.GestureGuideModalSheet
import com.example.ui.components.SecretMovieCard
import com.example.ui.model.Screen
import com.example.ui.theme.AccentGold
import com.example.ui.theme.CardBackgroundDark
import com.example.ui.theme.CardBackgroundElevated
import com.example.ui.theme.DarkIndigo
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.SoftPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.GameViewModel

@Composable
fun ActiveRoundScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val movie by viewModel.currentMovie.collectAsStateWithLifecycle()
    val timeRemaining by viewModel.timeRemaining.collectAsStateWithLifecycle()
    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()
    val isHintRevealed by viewModel.isHintRevealed.collectAsStateWithLifecycle()
    val isCardVisible by viewModel.isSecretCardVisible.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val participants by viewModel.gameParticipants.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentParticipantIndex.collectAsStateWithLifecycle()
    val roundNumber by viewModel.currentRoundNumber.collectAsStateWithLifecycle()
    val totalRounds by viewModel.totalRoundsInGame.collectAsStateWithLifecycle()

    val currentParticipant = participants.getOrNull(currentIndex)
    var showGesturesSheet by remember { mutableStateOf(false) }
    var showQuitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showQuitDialog = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkIndigo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .testTag("active_round_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CardBackgroundDark
            ) {
                Text(
                    text = "Round $roundNumber / $totalRounds",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AccentGold
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(currentParticipant?.colorHex ?: 0xFF6200EE).copy(alpha = 0.2f)
            ) {
                Text(
                    text = "Acting: ${currentParticipant?.name ?: "Team"}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(currentParticipant?.colorHex ?: 0xFF6200EE)
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            IconButton(onClick = { showGesturesSheet = true }) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Gesture Guide",
                    tint = ElectricCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Circular Timer with controls
        CircularTimerRing(
            timeRemaining = timeRemaining,
            totalTime = settings.roundDurationSec,
            isRunning = isTimerRunning,
            size = 145.dp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Timer actions: Pause/Resume, +15s
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CardBackgroundDark
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (isTimerRunning) viewModel.pauseTimer() else viewModel.resumeTimer()
                        },
                        modifier = Modifier.testTag("timer_play_pause_button")
                    ) {
                        Icon(
                            imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isTimerRunning) "Pause Timer" else "Resume Timer",
                            tint = if (isTimerRunning) AccentGold else NeonGreen
                        )
                    }

                    Text(
                        text = if (isTimerRunning) "Pause" else "Resume",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CardBackgroundDark
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .testTag("bonus_time_button"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.addBonusSeconds(15) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreTime,
                            contentDescription = "Add 15s",
                            tint = ElectricCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+15s Time",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Secret Movie Card
        movie?.let { currentMovie ->
            SecretMovieCard(
                movie = currentMovie,
                isVisible = isCardVisible,
                onToggleVisibility = { viewModel.toggleSecretCardVisibility() }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Actor Hint Clue Card (Earn 5 pts instead of 10)
            ActorHintCard(
                hint = currentMovie.hint,
                secondaryHint = currentMovie.secondaryHint,
                isRevealed = isHintRevealed,
                onRevealHint = { viewModel.revealHint() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons: Correct (+10 / +5) vs Pass (+0)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Pass / Skip Button
            OutlinedButton(
                onClick = { viewModel.markSkipOrPass() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .testTag("btn_pass_round"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = NeonCoral
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(NeonCoral)
                )
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Pass (0 pts)",
                    fontWeight = FontWeight.Bold
                )
            }

            // Correct Button
            val pointsToAward = if (isHintRevealed) 5 else 10
            Button(
                onClick = { viewModel.markCorrectGuess() },
                modifier = Modifier
                    .weight(1.3f)
                    .height(56.dp)
                    .testTag("btn_correct_round"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isHintRevealed) AccentGold else NeonGreen,
                    contentColor = DarkIndigo
                )
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Guessed! (+$pointsToAward pts)",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    if (showGesturesSheet) {
        GestureGuideModalSheet(onDismiss = { showGesturesSheet = false })
    }

    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("Leave Round?") },
            text = { Text("Quitting will return to the home screen and end the active round.") },
            confirmButton = {
                Button(
                    onClick = {
                        showQuitDialog = false
                        viewModel.pauseTimer()
                        viewModel.navigateTo(Screen.Home)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCoral)
                ) {
                    Text("Exit to Home")
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuitDialog = false }) {
                    Text("Resume Round", color = AccentGold)
                }
            },
            containerColor = DeepPurple,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary
        )
    }
}
