package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE category = :category ORDER BY id ASC")
    fun getMoviesByCategory(category: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE category IN (:categories) ORDER BY RANDOM()")
    suspend fun getShuffledMoviesByCategories(categories: List<String>): List<MovieEntity>

    @Query("SELECT * FROM movies ORDER BY RANDOM()")
    suspend fun getAllShuffledMovies(): List<MovieEntity>

    @Query("SELECT DISTINCT category FROM movies ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovieById(id: Long)

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' OR hint LIKE '%' || :query || '%' ORDER BY title ASC")
    fun searchMovies(query: String): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies WHERE isCustom = 1")
    suspend fun deleteCustomMovies()
}
