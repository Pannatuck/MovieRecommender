package dev.pan.movierecommender.presenter.data

import dev.pan.movierecommender.data.network.models.details.Details
import dev.pan.movierecommender.data.network.models.nowPlaying.NowPlaying
import dev.pan.movierecommender.data.network.models.popular.Popular

data class HomeState(
    val popular: Popular? = null,
    val details: Details? = null,
    val nowPlaying: NowPlaying? = null
)