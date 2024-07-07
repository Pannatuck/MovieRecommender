package dev.pan.movierecommender.data.network

import dev.pan.movierecommender.data.network.models.details.Details
import dev.pan.movierecommender.data.network.models.genres.Genres
import dev.pan.movierecommender.data.network.models.nowPlaying.NowPlaying
import dev.pan.movierecommender.data.network.models.popular.Popular
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //    https://api.themoviedb.org/3/movie/popular
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<Popular>

    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") movie_id: Int,
    ): Response<Details>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<NowPlaying>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String,
    ): Response<Genres>
}