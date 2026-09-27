package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.GameRecordEntity
import com.example.data.model.MovieEntity
import com.example.data.repository.GameRepository
import com.example.data.repository.MovieRepository
import com.example.ui.model.GameMode
import com.example.ui.model.GameSettings
import com.example.ui.model.Participant
import com.example.ui.model.RoundRecord
import com.example.ui.model.Screen
import com.example.util.SoundVibeHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val movieRepository = MovieRepository(db.movieDao())
    private val gameRepository = GameRepository(db.gameRecordDao())
    val soundVibeHelper = SoundVibeHelper(application)

    // Screens navigation
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Game Settings
    private val _settings = MutableStateFlow(GameSettings())
    val settings: StateFlow<GameSettings> = _settings.asStateFlow()

    // Active Game State
    private val _gameParticipants = MutableStateFlow<List<Participant>>(emptyList())
    val gameParticipants: StateFlow<List<Participant>> = _gameParticipants.asStateFlow()

    private val _currentParticipantIndex = MutableStateFlow(0)
    val currentParticipantIndex: StateFlow<Int> = _currentParticipantIndex.asStateFlow()

    private val _currentRoundNumber = MutableStateFlow(1)
    val currentRoundNumber: StateFlow<Int> = _currentRoundNumber.asStateFlow()

    private val _totalRoundsInGame = MutableStateFlow(6)
    val totalRoundsInGame: StateFlow<Int> = _totalRoundsInGame.asStateFlow()

    private val _movieQueue = MutableStateFlow<List<MovieEntity>>(emptyList())
    private val _currentMovie = MutableStateFlow<MovieEntity?>(null)
    val currentMovie: StateFlow<MovieEntity?> = _currentMovie.asStateFlow()

    // Round Timer & Clues
    private val _timeRemaining = MutableStateFlow(60)
    val timeRemaining: StateFlow<Int> = _timeRemaining.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _isHintRevealed = MutableStateFlow(false)
    val isHintRevealed: StateFlow<Boolean> = _isHintRevealed.asStateFlow()

    private val _isSecretCardVisible = MutableStateFlow(true)
    val isSecretCardVisible: StateFlow<Boolean> = _isSecretCardVisible.asStateFlow()

    private var timerJob: Job? = null

    // Round history in active session
    private val _roundHistory = MutableStateFlow<List<RoundRecord>>(emptyList())
    val roundHistory: StateFlow<List<RoundRecord>> = _roundHistory.asStateFlow()

    private val _lastRoundRecord = MutableStateFlow<RoundRecord?>(null)
    val lastRoundRecord: StateFlow<RoundRecord?> = _lastRoundRecord.asStateFlow()

    // Database Flows
    val allMovies = movieRepository.allMovies.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allGameHistory: StateFlow<List<GameRecordEntity>> = gameRepository.allRecords.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Movie Library Search & Category
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilterCategory = MutableStateFlow("All")
    val selectedFilterCategory: StateFlow<String> = _selectedFilterCategory.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.ensureDefaultMoviesLoaded()
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundVibeHelper.release()
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    // Settings actions
    fun setGameMode(mode: GameMode) {
        val newParticipants = if (mode == GameMode.TEAMS) {
            GameSettings.defaultTeams()
        } else {
            GameSettings.defaultIndividuals()
        }
        _settings.value = _settings.value.copy(
            mode = mode,
            participants = newParticipants
        )
    }

    fun updateParticipants(list: List<Participant>) {
        _settings.value = _settings.value.copy(participants = list)
    }

    fun addParticipant(name: String) {
        val current = _settings.value.participants
        val colors = listOf(
            0xFF6200EE, 0xFF03DAC5, 0xFFFF9800, 0xFFE91E63,
            0xFF4CAF50, 0xFF2196F3, 0xFF9C27B0, 0xFFFF5722
        )
        val nextColor = colors[current.size % colors.size]
        val newPart = Participant(
            id = if (current.isEmpty()) 1 else current.maxOf { it.id } + 1,
            name = name.ifEmpty { "Team ${current.size + 1}" },
            colorHex = nextColor
        )
        _settings.value = _settings.value.copy(participants = current + newPart)
    }

    fun removeParticipant(id: Int) {
        val current = _settings.value.participants
        if (current.size > 2) {
            _settings.value = _settings.value.copy(participants = current.filter { it.id != id })
        }
    }

    fun setRoundDuration(seconds: Int) {
        _settings.value = _settings.value.copy(roundDurationSec = seconds)
    }

    fun setRoundsPerParticipant(rounds: Int) {
        _settings.value = _settings.value.copy(totalRoundsPerParticipant = rounds)
    }

    fun toggleCategorySelection(category: String) {
        val current = _settings.value.selectedCategories.toMutableSet()
        if (category == "All Categories") {
            current.clear()
            current.add("All Categories")
        } else {
            current.remove("All Categories")
            if (current.contains(category)) {
                current.remove(category)
                if (current.isEmpty()) current.add("All Categories")
            } else {
                current.add(category)
            }
        }
        _settings.value = _settings.value.copy(selectedCategories = current)
    }

    fun toggleSound() {
        _settings.value = _settings.value.copy(soundEnabled = !_settings.value.soundEnabled)
    }

    fun toggleVibration() {
        _settings.value = _settings.value.copy(vibrationEnabled = !_settings.value.vibrationEnabled)
    }

    // GAMEPLAY FLOW
    fun startNewGame() {
        viewModelScope.launch {
            val cats = _settings.value.selectedCategories.toList()
            val fetchedMovies = movieRepository.getShuffledMovies(cats)
            _movieQueue.value = fetchedMovies.shuffled()

            val participantsCopy = _settings.value.participants.map {
                it.copy(score = 0, roundsPlayed = 0, correctGuesses = 0, hintsUsed = 0)
            }
            _gameParticipants.value = participantsCopy
            _currentParticipantIndex.value = 0
            _currentRoundNumber.value = 1
            _totalRoundsInGame.value = participantsCopy.size * _settings.value.totalRoundsPerParticipant
            _roundHistory.value = emptyList()

            prepareNextMovie()
            navigateTo(Screen.TurnPass)
        }
    }

    private fun prepareNextMovie() {
        var queue = _movieQueue.value
        if (queue.isEmpty()) {
            // Refill queue if needed
            viewModelScope.launch {
                val cats = _settings.value.selectedCategories.toList()
                val refill = movieRepository.getShuffledMovies(cats)
                _movieQueue.value = refill.shuffled()
                _currentMovie.value = _movieQueue.value.firstOrNull()
            }
        } else {
            _currentMovie.value = queue.first()
            _movieQueue.value = queue.drop(1)
        }
    }

    fun startTurn() {
        _timeRemaining.value = _settings.value.roundDurationSec
        _isHintRevealed.value = false
        _isSecretCardVisible.value = true
        _isTimerRunning.value = true
        navigateTo(Screen.ActiveRound)
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive && _timeRemaining.value > 0) {
                delay(1000)
                if (_isTimerRunning.value) {
                    _timeRemaining.value -= 1
                    if (_settings.value.soundEnabled) {
                        if (_timeRemaining.value in 1..5) {
                            soundVibeHelper.playUrgentTick()
                        } else if (_timeRemaining.value > 5) {
                            soundVibeHelper.playTick()
                        }
                    }
                }
            }
            if (_timeRemaining.value <= 0) {
                // Time up!
                _isTimerRunning.value = false
                if (_settings.value.soundEnabled) {
                    soundVibeHelper.playTimeUp()
                }
                recordRoundResult(isSuccess = false)
            }
        }
    }

    fun pauseTimer() {
        _isTimerRunning.value = false
    }

    fun resumeTimer() {
        if (_timeRemaining.value > 0) {
            _isTimerRunning.value = true
        }
    }

    fun addBonusSeconds(bonus: Int = 15) {
        _timeRemaining.value += bonus
        soundVibeHelper.playHint()
    }

    fun revealHint() {
        if (!_isHintRevealed.value) {
            _isHintRevealed.value = true
            if (_settings.value.soundEnabled) {
                soundVibeHelper.playHint()
            }
        }
    }

    fun toggleSecretCardVisibility() {
        _isSecretCardVisible.value = !_isSecretCardVisible.value
    }

    fun markCorrectGuess() {
        timerJob?.cancel()
        _isTimerRunning.value = false
        if (_settings.value.soundEnabled) {
            soundVibeHelper.playSuccess()
        }
        recordRoundResult(isSuccess = true)
    }

    fun markSkipOrPass() {
        timerJob?.cancel()
        _isTimerRunning.value = false
        if (_settings.value.soundEnabled) {
            soundVibeHelper.playSkip()
        }
        recordRoundResult(isSuccess = false)
    }

    private fun recordRoundResult(isSuccess: Boolean) {
        val movie = _currentMovie.value ?: return
        val currentParticipants = _gameParticipants.value.toMutableList()
        val actor = currentParticipants[_currentParticipantIndex.value]

        // SCORING RULE:
        // 10 points for winning / correct guess without hint.
        // If hint used, marks will be 5.
        // 0 points for pass / time out.
        val points = if (isSuccess) {
            if (_isHintRevealed.value) 5 else 10
        } else {
            0
        }

        val updatedActor = actor.copy(
            score = actor.score + points,
            roundsPlayed = actor.roundsPlayed + 1,
            correctGuesses = if (isSuccess) actor.correctGuesses + 1 else actor.correctGuesses,
            hintsUsed = if (_isHintRevealed.value) actor.hintsUsed + 1 else actor.hintsUsed
        )
        currentParticipants[_currentParticipantIndex.value] = updatedActor
        _gameParticipants.value = currentParticipants

        val record = RoundRecord(
            roundNumber = _currentRoundNumber.value,
            actorName = actor.name,
            teamOrPlayerName = actor.name,
            movie = movie,
            hintUsed = _isHintRevealed.value,
            pointsEarned = points,
            timeRemainingSec = _timeRemaining.value,
            isSuccess = isSuccess
        )
        _lastRoundRecord.value = record
        _roundHistory.value = _roundHistory.value + record

        navigateTo(Screen.RoundSummary)
    }

    fun nextTurnOrFinish() {
        if (_currentRoundNumber.value >= _totalRoundsInGame.value) {
            // End of Game!
            saveCompletedGame()
            navigateTo(Screen.GameOver)
        } else {
            _currentRoundNumber.value += 1
            _currentParticipantIndex.value =
                (_currentParticipantIndex.value + 1) % _gameParticipants.value.size
            prepareNextMovie()
            navigateTo(Screen.TurnPass)
        }
    }

    private fun saveCompletedGame() {
        viewModelScope.launch {
            val participants = _gameParticipants.value.sortedByDescending { it.score }
            val winner = participants.firstOrNull() ?: return@launch
            val summary = participants.joinToString(",") { "${it.name}:${it.score}" }

            gameRepository.saveGameRecord(
                gameMode = _settings.value.mode.name,
                winnerName = winner.name,
                winningScore = winner.score,
                totalRounds = _totalRoundsInGame.value,
                teamsJson = summary
            )
        }
    }

    // Movie Library actions
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilterCategory(category: String) {
        _selectedFilterCategory.value = category
    }

    fun addCustomMovie(
        title: String,
        category: String,
        hint: String,
        secondaryHint: String,
        difficulty: String,
        year: Int
    ) {
        viewModelScope.launch {
            movieRepository.addCustomMovie(
                title = title,
                category = category,
                hint = hint,
                secondaryHint = secondaryHint,
                difficulty = difficulty,
                year = year
            )
        }
    }

    fun deleteMovie(id: Long) {
        viewModelScope.launch {
            movieRepository.deleteMovie(id)
        }
    }

    fun clearGameHistory() {
        viewModelScope.launch {
            gameRepository.clearAll()
        }
    }
}
