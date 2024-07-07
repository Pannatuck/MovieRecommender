package dev.pan.movierecommender.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.pan.movierecommender.data.network.models.details.Genre

@Entity
data class MovieEntity(
    val adult: Boolean,
    val releaseDate: String,
    val originalLanguage: String,
    @TypeConverters(GenreConverter::class)
    val genres: List<Genre>?,
    val voteAverage: Double,
    val title: String,
    val backdropPath: String,
    @PrimaryKey
    val movieId: Int
    )
