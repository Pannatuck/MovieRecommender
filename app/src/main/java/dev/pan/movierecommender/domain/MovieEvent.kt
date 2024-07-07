package dev.pan.movierecommender.domain

import dev.pan.movierecommender.data.db.MovieEntity

sealed interface MovieEvent {
    // TODO: all needed events
    // all events user can do on screen (button press, toggles, etc)
    object SaveMovie : MovieEvent
    data class DeleteMovie(val movie: MovieEntity) : MovieEvent
    data class GetMovieDetails(val movieId: Int) : MovieEvent
    object GetMovies : MovieEvent

    // also can be used for something like
    // object ShowDialog: MovieEvent
    // object HideDialog: MovieEvent

    /* or like this
     data class SortMovies(val sortType: SortType) : MovieEvent

     and have enum class that will be used to choose from
     enum class SortType { POPULARITY, RATING, VOTE_COUNT }
    */
}