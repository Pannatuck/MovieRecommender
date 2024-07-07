package dev.pan.movierecommender.presenter

import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.pan.movierecommender.R
import dev.pan.movierecommender.bodyFontFamily
import dev.pan.movierecommender.data.db.MovieEntity
import dev.pan.movierecommender.presenter.data.HomeState
import dev.pan.movierecommender.util.Constants
import kotlinx.parcelize.Parcelize


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    id: Int?,
    navController: NavController,
    state: HomeState,
    viewModel: HomeViewModel
) {
    val favoriteState by viewModel.isFavorite.collectAsState()
    val isFavorite = favoriteState[state.details?.id ?: 0]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // TODO: details state movie_id is not same as popular movie_id for some reason
            viewModel.getDetails(id ?: 0)

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        // THIS
                        "https://image.tmdb.org/t/p/w500" + state.details?.backdrop_path
                    )
                    .addHeader("Authorization", "Bearer ${Constants.API_KEY}")
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)// it's the same even removing comments
                    .build(),
                contentDescription = "poster image",
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )


            )
            Column(
                Modifier
                    .padding(start = 18.dp, top = 8.dp, end = 18.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = state.details?.title ?: "",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontFamily = bodyFontFamily,
                        modifier = Modifier
                            .weight(9f)
                            .padding(top = 4.dp) // to be aligned with iconButton that has internal padding
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(0.dp),
                        onClick = { viewModel.toggleFavorite(
                            MovieEntity(
                                adult = state.details?.adult ?: false,
                                releaseDate = state.details?.release_date ?: "",
                                originalLanguage = state.details?.original_language ?: "",
                                genres = state.details?.genres,
                                voteAverage = state.details?.vote_average ?: 0.0,
                                title = state.details?.title ?: "",
                                backdropPath = state.details?.backdrop_path ?: "",
                                movieId = state.details?.id ?: 0
                            ))
                        }
                    ) {
                        Icon(
                            imageVector = if(isFavorite == true) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite == true) "Remove from favorite" else "Add to favorite",
                            tint = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                    Text(text = state.details?.vote_average.toString(), fontFamily = bodyFontFamily)
                }
                Spacer(modifier = Modifier.height(8.dp))
                // TODO: reformat to pass as separate state
                LazyRow {
                    itemsIndexed(state.details?.genres.orEmpty()) { index, item ->
                        val allGenres = state.genres?.genres

                        val genreName = allGenres?.find { it.id == item.id }?.name
                        Text(
                            text = genreName.toString(),
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape
                                )
                                .padding(10.dp),
                            fontSize = 12.sp,
                            fontFamily = bodyFontFamily,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(IntrinsicSize.Min)

                ) {
                    Column( 
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Language",
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(text = state.details?.original_language ?: "", fontFamily = bodyFontFamily)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Release date",
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = state.details?.release_date ?: "",
                            fontFamily = bodyFontFamily
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_adult),
                            contentDescription = null,
                            modifier = Modifier.height(19.dp)
                        )
                        if (state.details?.adult == true) {
                            Text(
                                text = "Yes",
                                fontFamily = bodyFontFamily
                            )
                        } else {
                            Text(
                                text = "No",
                                fontFamily = bodyFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}