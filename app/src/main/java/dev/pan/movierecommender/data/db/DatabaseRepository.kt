package dev.pan.movierecommender.data.db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(private val movieDAO: MovieDAO) {

    suspend fun saveMovie(movie: MovieEntity) = movieDAO.saveMovie(movie)

    suspend fun deleteMovie(movie: MovieEntity) = movieDAO.deleteMovie(movie)

    suspend fun getAllMovies() = movieDAO.getAllMovies()

    suspend fun getMovieByTitle(title: String) = movieDAO.getMovieByTitle(title)

}

