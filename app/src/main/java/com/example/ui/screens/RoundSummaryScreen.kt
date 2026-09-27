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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassDisabled
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.SoftPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.GameViewModel

@Composable
fun RoundSummaryScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val lastRound by viewModel.lastRoundRecord.collectAsStateWithLifecycle()
    val participants by viewModel.gameParticipants.collectAsStateWithLifecycle()
    val currentRoundNumber by viewModel.currentRoundNumber.collectAsStateWithLifecycle()
    val totalRounds by viewModel.totalRoundsInGame.collectAsStateWithLifecycle()
    val isLastRound = currentRoundNumber >= totalRounds

    BackHandler {
        // Prevent accidental back out of the game loop
        viewModel.nextTurnOrFinish()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkIndigo)
            .padding(20.dp)
            .testTag("round_summary_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Round header
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
                    text = "Round $currentRoundNumber of $totalRounds Complete",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AccentGold
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Outcome Card
        lastRound?.let { round ->
            val isSuccess = round.isSuccess
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("round_outcome_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        if (isSuccess) listOf(NeonGreen, AccentGold) else listOf(NeonCoral, AccentGold)
                    ),
                    width = 1.5.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSuccess) NeonGreen.copy(alpha = 0.2f) else NeonCoral.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.HourglassDisabled,
                            contentDescription = null,
                            tint = if (isSuccess) NeonGreen else NeonCoral,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isSuccess) {
                            if (round.hintUsed) "+5 Points (Hint Used)" else "+10 Points! Great Guess!"
                        } else {
                            "0 Points (Time Expired / Skipped)"
                        },
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = if (isSuccess) NeonGreen else NeonCoral
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = round.movie.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Category: ${round.movie.category} • Team: ${round.teamOrPlayerName}",
                        style = MaterialTheme.typography.bodySmall.copy(color = SoftPurple),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Leaderboard title
        Text(
            text = "Leaderboard & Current Scores",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = AccentGold
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Scoreboard
        ScoreboardList(
            participants = participants,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Next Action Button
        Button(
            onClick = { viewModel.nextTurnOrFinish() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("btn_next_turn"),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLastRound) AccentGold else NeonGreen,
                contentColor = DarkIndigo
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = if (isLastRound) Icons.Default.Celebration else Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isLastRound) "Finish Game & View Winner Podium!" else "Pass to Next Team / Player",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
        }
    }
}
