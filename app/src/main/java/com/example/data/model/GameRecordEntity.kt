package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_records")
data class GameRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val gameMode: String, // "TEAM" or "INDIVIDUAL"
    val winnerName: String,
    val winningScore: Int,
    val totalRounds: Int,
    val teamsJson: String // Serialized summary: name:score,name:score
)
