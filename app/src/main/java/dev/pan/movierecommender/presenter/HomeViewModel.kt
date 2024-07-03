package dev.pan.movierecommender.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pan.movierecommender.data.network.ApiRepository
import dev.pan.movierecommender.presenter.data.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        getPopular()
        getNowPlaying()
    }

    // TODO: get a test call from api to check if all works
    fun getPopular(){
        viewModelScope.launch {
            apiRepository.getPopular("en-US", 1).let {
                _homeState.value.copy(
                    popular = it.body()
                )
            }
        }
    }

    fun getDetails(movie_id: Int){
        viewModelScope.launch {
            apiRepository.getDetails(movie_id).let {
                _homeState.value.copy(
                    details = it.body()
                )
            }
        }
    }

    fun getNowPlaying(){
        viewModelScope.launch {
            apiRepository.getNowPlaying("en-US", 1).let {
                _homeState.value.copy(
                    nowPlaying = it.body()
                )
            }
        }
    }

}