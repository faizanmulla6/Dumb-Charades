package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ScoreboardList
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
fun GameOverScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val participants by viewModel.gameParticipants.collectAsStateWithLifecycle()
    val sorted = participants.sortedByDescending { it.score }
    val winner = sorted.firstOrNull()
    val totalRounds by viewModel.totalRoundsInGame.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.navigateTo(Screen.Home)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkIndigo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .testTag("game_over_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Trophy / Fireworks Icon
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(AccentGold.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = AccentGold,
                modifier = Modifier.size(54.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Game Finished!",
            style = MaterialTheme.typography.titleMedium.copy(
                color = SoftPurple,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "${winner?.name ?: "Champion"} Wins!",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                color = AccentGold,
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = "With a grand total of ${winner?.score ?: 0} points!",
            style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Podium top 3 (if at least 2 participants)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundDark),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.horizontalGradient(listOf(ElectricCyan, AccentGold, NeonCoral)),
                width = 1.5.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🏆 Winner Podium",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AccentGold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // 2nd Place (Left)
                    if (sorted.size >= 2) {
                        PodiumColumn(
                            rank = "2nd",
                            name = sorted[1].name,
                            score = sorted[1].score,
                            color = ElectricCyan,
                            height = 95.dp
                        )
                    }

                    // 1st Place (Center)
                    winner?.let {
                        PodiumColumn(
                            rank = "1st",
                            name = it.name,
                            score = it.score,
                            color = AccentGold,
                            height = 125.dp
                        )
                    }

                    // 3rd Place (Right)
                    if (sorted.size >= 3) {
                        PodiumColumn(
                            rank = "3rd",
                            name = sorted[2].name,
                            score = sorted[2].score,
                            color = NeonCoral,
                            height = 75.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Complete Standings
        Text(
            text = "Full Match Standings",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ScoreboardList(
            participants = sorted,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Play Again Button
        Button(
            onClick = { viewModel.startNewGame() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("btn_play_again"),
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonGreen,
                contentColor = DarkIndigo
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Replay, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Play Rematch (Same Settings)",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Return Home Button
        OutlinedButton(
            onClick = { viewModel.navigateTo(Screen.Home) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_return_home"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Main Menu", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // History
        Button(
            onClick = { viewModel.navigateTo(Screen.GameHistory) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_view_history_from_game_over"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CardBackgroundElevated,
                contentColor = SoftPurple
            )
        ) {
            Icon(imageVector = Icons.Default.History, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("View All Match History", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun PodiumColumn(
    rank: String,
    name: String,
    score: Int,
    color: Color,
    height: androidx.compose.ui.unit.Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(90.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            ),
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = "$score pts",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                color = color
            )
        )
        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .width(70.dp)
                .height(height)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(color.copy(alpha = 0.25f)),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                shape = CircleShape,
                color = color,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(28.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = rank,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = DeepPurple
                        )
                    )
                }
            }
        }
    }
}
