package com.example.ui.model

import com.example.data.model.MovieEntity

enum class GameMode(val displayName: String, val subtitle: String) {
    TEAMS("Team Mode", "Compete as 2 to 6 teams with rotating actors"),
    INDIVIDUALS("Individual Mode", "Play solo free-for-all, pass phone between players")
}

data class Participant(
    val id: Int,
    val name: String,
    val colorHex: Long,
    var score: Int = 0,
    var roundsPlayed: Int = 0,
    var correctGuesses: Int = 0,
    var hintsUsed: Int = 0
)

data class RoundRecord(
    val roundNumber: Int,
    val actorName: String,
    val teamOrPlayerName: String,
    val movie: MovieEntity,
    val hintUsed: Boolean,
    val pointsEarned: Int,
    val timeRemainingSec: Int,
    val isSuccess: Boolean
)

data class GameSettings(
    val mode: GameMode = GameMode.TEAMS,
    val participants: List<Participant> = defaultTeams(),
    val roundDurationSec: Int = 60,
    val totalRoundsPerParticipant: Int = 3, // Each team gets 3 turns
    val selectedCategories: Set<String> = setOf("All Categories"),
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
) {
    companion object {
        fun defaultTeams(): List<Participant> = listOf(
            Participant(1, "Team Alpha", 0xFF6200EE),
            Participant(2, "Team Beta", 0xFF03DAC5)
        )

        fun defaultIndividuals(): List<Participant> = listOf(
            Participant(1, "Player 1", 0xFF6200EE),
            Participant(2, "Player 2", 0xFF03DAC5),
            Participant(3, "Player 3", 0xFFFF9800)
        )
    }
}

sealed class Screen {
    object Home : Screen()
    object GameSetup : Screen()
    object TurnPass : Screen()
    object ActiveRound : Screen()
    object RoundSummary : Screen()
    object GameOver : Screen()
    object MovieLibrary : Screen()
    object GameHistory : Screen()
    object RulesGuide : Screen()
}

data class CharadesGesture(
    val title: String,
    val gestureAction: String,
    val meaning: String,
    val iconName: String
)

object CharadesGuideData {
    val gestures = listOf(
        CharadesGesture(
            title = "Movie Title",
            gestureAction = "Pretend to crank an antique movie projector with both fists rolling.",
            meaning = "Signals that the secret item is a Movie!",
            iconName = "movie"
        ),
        CharadesGesture(
            title = "Number of Words",
            gestureAction = "Hold up that number of fingers horizontally or vertically.",
            meaning = "Tells your team how many words are in the title.",
            iconName = "pin"
        ),
        CharadesGesture(
            title = "Which Word",
            gestureAction = "Hold up 1 finger for 1st word, 2 for 2nd word, etc.",
            meaning = "Specifies which word in the title you are acting right now.",
            iconName = "filter_1"
        ),
        CharadesGesture(
            title = "Syllables Count",
            gestureAction = "Lay fingers across your forearm to count syllables in the word.",
            meaning = "Shows how many syllables are in the active word.",
            iconName = "linear_scale"
        ),
        CharadesGesture(
            title = "Sounds Like / Rhymes",
            gestureAction = "Cup one hand behind your ear like listening closely.",
            meaning = "The word you are acting rhymes with or sounds like the target word.",
            iconName = "hearing"
        ),
        CharadesGesture(
            title = "Small / Connecting Word",
            gestureAction = "Pinch thumb and index finger close together ('the', 'a', 'in', 'of').",
            meaning = "Indicates a short grammatical word like 'the', 'and', 'to'.",
            iconName = "pinch"
        ),
        CharadesGesture(
            title = "Opposite Meaning",
            gestureAction = "Form two fists and bump knuckles together or roll hands away.",
            meaning = "The clue is the exact opposite of what is guessed.",
            iconName = "swap_horiz"
        ),
        CharadesGesture(
            title = "Getting Warmer / Close!",
            gestureAction = "Roll hands rapidly forward or wave hands fan-like toward face.",
            meaning = "The guessers are very close! Keep guessing around that idea!",
            iconName = "local_fire_department"
        )
    )
}
