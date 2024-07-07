package dev.pan.movierecommender.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pan.movierecommender.data.db.DatabaseRepository
import dev.pan.movierecommender.data.db.MovieEntity
import dev.pan.movierecommender.data.network.ApiRepository
import dev.pan.movierecommender.presenter.data.HomeState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private val _movies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies: StateFlow<List<MovieEntity>> = _movies

    private val _isFavorite = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val isFavorite: StateFlow<Map<Int,Boolean>> = _isFavorite.asStateFlow()

    init {
        getPopular()
        getNowPlaying()
        getGenres()

        viewModelScope.launch {
            databaseRepository.getAllMovies().collect { movies ->
                _movies.value = movies
            }
        }

    }

    // Remote

    // TODO: get a test call from api to check if all works
    fun getPopular(){
        viewModelScope.launch {
            try {
                val response = apiRepository.getPopular("en-US", 1)
                if (response.isSuccessful){
                    _homeState.update { currentState ->
                        currentState.copy(popular = response.body())
                    }
                }
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

    fun getDetails(movie_id: Int){
        viewModelScope.launch {
            try {
                val response = apiRepository.getDetails(movie_id)
                if (response.isSuccessful){
                    _homeState.update { currentState ->
                        currentState.copy(details = response.body())
                    }
                }
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

    fun getNowPlaying(){
        viewModelScope.launch {
            try {
                val response = apiRepository.getNowPlaying("en-US", 1)
                if (response.isSuccessful){
                    _homeState.update { currentState ->
                        currentState.copy(nowPlaying = response.body())
                    }
                }
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

    fun getGenres(){
        viewModelScope.launch {
            try {
                val response = apiRepository.getGenres("en-US")
                if (response.isSuccessful){
                    _homeState.update { currentState ->
                        currentState.copy(genres = response.body())
                    }
                }
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

    // Local

    fun saveMovie(movie: MovieEntity){
        viewModelScope.launch {
            databaseRepository.saveMovie(movie)
        }
    }

    fun deleteMovie(movie: MovieEntity){
        viewModelScope.launch {
            databaseRepository.deleteMovie(movie)
        }
    }

    fun getMovieByTitle(title: String, onResult: (MovieEntity?) -> Unit) {
        viewModelScope.launch {
            val movie = databaseRepository.getMovieByTitle(title)
            onResult(movie)
        }
    }

    fun toggleFavorite(movie: MovieEntity){
        viewModelScope.launch {
            val isFavorite = _isFavorite.value[movie.movieId] ?: false
            if (isFavorite) {
                databaseRepository.deleteMovie(movie)
            } else {
                databaseRepository.saveMovie(movie)
            }
            _isFavorite.value = _isFavorite.value.toMutableMap().apply {
                put(movie.movieId, !isFavorite)
            }
        }
    }









}