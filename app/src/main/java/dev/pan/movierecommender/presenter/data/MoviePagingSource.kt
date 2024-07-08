package dev.pan.movierecommender.presenter.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.pan.movierecommender.data.network.ApiRepository
import dev.pan.movierecommender.data.network.models.nowPlaying.Result
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val apiRepository: ApiRepository
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?: 1
            val response = apiRepository.getNowPlaying("en-US", nextPage)

            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                LoadResult.Page(
                    data = movies,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (movies.isEmpty()) null else nextPage + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to load movies"))
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}

