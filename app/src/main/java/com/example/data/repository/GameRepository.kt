package com.example.data.repository

import com.example.data.dao.GameRecordDao
import com.example.data.model.GameRecordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GameRepository(private val gameRecordDao: GameRecordDao) {
    val allRecords: Flow<List<GameRecordEntity>> = gameRecordDao.getAllGameRecords()

    suspend fun saveGameRecord(
        gameMode: String,
        winnerName: String,
        winningScore: Int,
        totalRounds: Int,
        teamsJson: String
    ): Long {
        return withContext(Dispatchers.IO) {
            val record = GameRecordEntity(
                timestamp = System.currentTimeMillis(),
                gameMode = gameMode,
                winnerName = winnerName,
                winningScore = winningScore,
                totalRounds = totalRounds,
                teamsJson = teamsJson
            )
            gameRecordDao.insertGameRecord(record)
        }
    }

    suspend fun deleteRecord(id: Long) {
        withContext(Dispatchers.IO) {
            gameRecordDao.deleteGameRecord(id)
        }
    }

    suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            gameRecordDao.clearAll()
        }
    }
}
