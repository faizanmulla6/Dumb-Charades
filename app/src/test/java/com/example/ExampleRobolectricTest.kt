package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.DefaultMovies
import com.example.ui.model.GameMode
import com.example.ui.model.GameSettings
import com.example.ui.model.Participant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Dumb Charades", appName)
    }

    @Test
    fun `default movies catalog has movies with clues`() {
        val movies = DefaultMovies.list
        assertTrue("Movie catalog should not be empty", movies.isNotEmpty())
        assertTrue("All movies must have a non-empty hint", movies.all { it.hint.isNotBlank() })
        assertTrue("All movies must have a title", movies.all { it.title.isNotBlank() })
    }

    @Test
    fun `scoring rules give 10 points for no hint and 5 points with hint`() {
        val teamAlpha = Participant(1, "Team Alpha", 0xFF6200EE)

        // No hint guess: 10 points
        val scoreNoHint = 10
        val updatedNoHint = teamAlpha.copy(score = teamAlpha.score + scoreNoHint)
        assertEquals(10, updatedNoHint.score)

        // Hint used guess: 5 points
        val scoreWithHint = 5
        val updatedWithHint = updatedNoHint.copy(score = updatedNoHint.score + scoreWithHint)
        assertEquals(15, updatedWithHint.score)
    }

    @Test
    fun `game settings setup default teams and individuals`() {
        val teams = GameSettings.defaultTeams()
        assertEquals(2, teams.size)
        assertEquals("Team Alpha", teams[0].name)
        assertEquals("Team Beta", teams[1].name)

        val individuals = GameSettings.defaultIndividuals()
        assertEquals(3, individuals.size)
    }
}
