package dev.pan.movierecommender.data.network

import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopular(language: String, page: Int) = apiService.getPopular(language, page)

    suspend fun getDetails(movie_id: Int) = apiService.getDetails(movie_id)

    suspend fun getNowPlaying(language: String, page: Int) = apiService.getNowPlaying(language, page)

    suspend fun getGenres(language: String) = apiService.getGenres(language)

}