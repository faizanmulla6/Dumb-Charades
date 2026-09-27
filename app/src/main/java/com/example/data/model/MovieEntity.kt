package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val hint: String,
    val secondaryHint: String = "",
    val difficulty: String = "Medium", // Easy, Medium, Hard
    val year: Int = 0,
    val wordCount: Int = 1,
    val isCustom: Boolean = false
)
