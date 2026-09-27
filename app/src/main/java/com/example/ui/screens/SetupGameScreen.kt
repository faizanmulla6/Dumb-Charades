package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.model.GameMode
import com.example.ui.model.Participant
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SetupGameScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    var newParticipantName by remember { mutableStateOf("") }

    BackHandler {
        viewModel.navigateTo(Screen.Home)
    }

    val availableCategories = listOf(
        "All Categories",
        "Hollywood Hits",
        "Bollywood Blockbusters",
        "Animated & Family",
        "Sci-Fi & Action",
        "Comedy & Classics"
    )

    val durations = listOf(30, 45, 60, 90, 120)
    val roundOptions = listOf(2, 3, 5)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkIndigo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .testTag("setup_screen")
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(Screen.Home) },
                modifier = Modifier.testTag("back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Home",
                    tint = TextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Game Setup",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )
                )
                Text(
                    text = "Configure teams, timer & categories",
                    style = MaterialTheme.typography.bodySmall.copy(color = SoftPurple)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mode Switch (Teams vs Individuals)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundDark)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val isTeams = settings.mode == GameMode.TEAMS
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.setGameMode(GameMode.TEAMS) }
                        .testTag("mode_teams_chip"),
                    color = if (isTeams) AccentGold else CardBackgroundElevated
                ) {
                    Text(
                        text = "👥 Team Mode",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isTeams) DarkIndigo else TextSecondary
                        ),
                        modifier = Modifier.padding(vertical = 12.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.setGameMode(GameMode.INDIVIDUALS) }
                        .testTag("mode_individuals_chip"),
                    color = if (!isTeams) ElectricCyan else CardBackgroundElevated
                ) {
                    Text(
                        text = "👤 Solo Players",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (!isTeams) Color.White else TextSecondary
                        ),
                        modifier = Modifier.padding(vertical = 12.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Teams / Players List Section
        Text(
            text = if (settings.mode == GameMode.TEAMS) "Teams (${settings.participants.size})" else "Players (${settings.participants.size})",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
        )
        Text(
            text = "Each participant will rotate turns acting movies to teammates",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundDark)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                settings.participants.forEachIndexed { index, participant ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color(participant.colorHex))
                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        OutlinedTextField(
                            value = participant.name,
                            onValueChange = { newName ->
                                val updated = settings.participants.toMutableList()
                                updated[index] = participant.copy(name = newName)
                                viewModel.updateParticipants(updated)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("participant_name_input_${participant.id}"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricCyan,
                                unfocusedBorderColor = CardBackgroundElevated,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )

                        if (settings.participants.size > 2) {
                            IconButton(
                                onClick = { viewModel.removeParticipant(participant.id) },
                                modifier = Modifier.testTag("remove_participant_${participant.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove",
                                    tint = NeonCoral
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Add participant row
                if (settings.participants.size < 8) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = newParticipantName,
                            onValueChange = { newParticipantName = it },
                            placeholder = {
                                Text(
                                    if (settings.mode == GameMode.TEAMS) "Enter new team name" else "Enter player name",
                                    color = TextMuted
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("add_participant_input"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentGold,
                                unfocusedBorderColor = CardBackgroundElevated,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                viewModel.addParticipant(newParticipantName.trim())
                                newParticipantName = ""
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AccentGold, contentColor = DarkIndigo),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("add_participant_button")
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Timer Duration Section
        Text(
            text = "Timer Duration per Round",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            durations.forEach { sec ->
                val isSelected = settings.roundDurationSec == sec
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.setRoundDuration(sec) }
                        .testTag("duration_${sec}s"),
                    color = if (isSelected) AccentGold else CardBackgroundDark,
                    border = if (isSelected) null else CardDefaults.outlinedCardBorder()
                ) {
                    Text(
                        text = "${sec}s",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) DarkIndigo else TextSecondary
                        ),
                        modifier = Modifier.padding(vertical = 12.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Rounds per Participant
        Text(
            text = "Turns per Team / Player",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            roundOptions.forEach { r ->
                val isSelected = settings.totalRoundsPerParticipant == r
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.setRoundsPerParticipant(r) }
                        .testTag("turns_${r}"),
                    color = if (isSelected) ElectricCyan else CardBackgroundDark
                ) {
                    Text(
                        text = "$r Turns (${r * settings.participants.size} Total)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextSecondary
                        ),
                        modifier = Modifier.padding(vertical = 12.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Categories Selection
        Text(
            text = "Movie Categories",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
        )
        Text(
            text = "Select genres to shuffle movies from",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
        )
        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            availableCategories.forEach { category ->
                val isSelected = settings.selectedCategories.contains(category)
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.toggleCategorySelection(category) },
                    label = {
                        Text(
                            text = category,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    leadingIcon = if (isSelected) {
                        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    } else null,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = CardBackgroundDark,
                        labelColor = TextSecondary,
                        selectedContainerColor = AccentGold,
                        selectedLabelColor = DarkIndigo,
                        selectedLeadingIconColor = DarkIndigo
                    ),
                    modifier = Modifier.testTag("category_chip_$category")
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Sound & Haptic Feedback toggle
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundDark)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Timer Sound Effects",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )
                        Text(
                            text = "Audio ticks & buzzer when time expires",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                        )
                    }
                    Switch(
                        checked = settings.soundEnabled,
                        onCheckedChange = { viewModel.toggleSound() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AccentGold,
                            checkedTrackColor = CardBackgroundElevated
                        ),
                        modifier = Modifier.testTag("switch_sound")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Big Start Game Button
        Button(
            onClick = { viewModel.startNewGame() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("start_game_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonGreen,
                contentColor = DarkIndigo
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Start Charades Battle!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
