package dev.pan.movierecommender.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Upsert
    suspend fun saveMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE title = :title LIMIT 1")
    suspend fun getMovieByTitle(title: String): MovieEntity?
}