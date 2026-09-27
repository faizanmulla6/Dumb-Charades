package com.example.data.repository

import com.example.data.dao.MovieDao
import com.example.data.db.DefaultMovies
import com.example.data.model.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {

    val allMovies: Flow<List<MovieEntity>> = movieDao.getAllMovies()
    val allCategories: Flow<List<String>> = movieDao.getAllCategories()

    suspend fun ensureDefaultMoviesLoaded() {
        withContext(Dispatchers.IO) {
            val count = movieDao.getMovieCount()
            if (count == 0) {
                movieDao.insertMovies(DefaultMovies.list)
            }
        }
    }

    suspend fun getShuffledMovies(categories: List<String>): List<MovieEntity> {
        return withContext(Dispatchers.IO) {
            ensureDefaultMoviesLoaded()
            if (categories.isEmpty() || categories.contains("All Categories")) {
                movieDao.getAllShuffledMovies()
            } else {
                movieDao.getShuffledMoviesByCategories(categories)
            }
        }
    }

    suspend fun addCustomMovie(
        title: String,
        category: String,
        hint: String,
        secondaryHint: String,
        difficulty: String,
        year: Int
    ): Long {
        return withContext(Dispatchers.IO) {
            val wordCount = title.trim().split("\\s+".toRegex()).size
            val movie = MovieEntity(
                title = title.trim(),
                category = category.trim().ifEmpty { "Custom" },
                hint = hint.trim(),
                secondaryHint = secondaryHint.trim(),
                difficulty = difficulty,
                year = year,
                wordCount = wordCount,
                isCustom = true
            )
            movieDao.insertMovie(movie)
        }
    }

    suspend fun deleteMovie(id: Long) {
        withContext(Dispatchers.IO) {
            movieDao.deleteMovieById(id)
        }
    }

    fun searchMovies(query: String): Flow<List<MovieEntity>> {
        return movieDao.searchMovies(query)
    }

    suspend fun resetToDefaultMovies() {
        withContext(Dispatchers.IO) {
            movieDao.deleteCustomMovies()
            if (movieDao.getMovieCount() == 0) {
                movieDao.insertMovies(DefaultMovies.list)
            }
        }
    }
}
