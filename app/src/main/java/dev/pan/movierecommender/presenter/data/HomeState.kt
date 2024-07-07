package dev.pan.movierecommender.presenter.data

import dev.pan.movierecommender.data.network.models.details.Details
import dev.pan.movierecommender.data.network.models.genres.Genres
import dev.pan.movierecommender.data.network.models.nowPlaying.NowPlaying
import dev.pan.movierecommender.data.network.models.popular.Popular


// TODO: split into different states for each screen
data class HomeState(
    val popular: Popular? = null,
    val details: Details? = null,
    val nowPlaying: NowPlaying? = null,
    val genres: Genres? = null
)