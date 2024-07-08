package dev.pan.movierecommender.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.pan.movierecommender.CustomGold
import dev.pan.movierecommender.R
import dev.pan.movierecommender.bodyFontFamily
import dev.pan.movierecommender.presenter.data.HomeState
import dev.pan.movierecommender.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Movie Recommender",
                            style = MaterialTheme.typography.headlineMedium,
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                modifier = Modifier.height(56.dp)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                item {
                    NowShowingSection(navController = navController, state = state)
                }
                item {
                    PopularSection(navController = navController, state = state)
                }
            }
        }

    }

}

// TODO: change size of pictures to fit both sections on screen or make screen scrollable somehow
@Composable
fun NowShowingSection(
    navController: NavController,
    state: HomeState
) {
    Column(
    ) {
        // top text row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: title bar can be separate composable, it's repeated
            Text(
                text = "Now Showing",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See more",
                modifier = Modifier
                    .alpha(0.5f)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.shapes.extraLarge
                    )
                    .clickable { navController.navigate("nowShowing") }
                    .padding(10.dp),
                fontSize = 12.sp,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // big image scroll
        LazyRow(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {

            itemsIndexed(state.nowPlaying?.results ?: emptyList()) { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { navController.navigate("details/${item.id}") } 
                    
                ) {
                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(
                                    "https://image.tmdb.org/t/p/w500" + state.nowPlaying?.results?.get(
                                        index
                                    )?.poster_path
                                )
                                .addHeader("Authorization", "Bearer ${Constants.API_KEY}")
                                .crossfade(true)
                                .diskCachePolicy(CachePolicy.ENABLED)// it's the same even removing comments
                                .build(),
                            contentDescription = "poster image",
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .width(120.dp)
                                .height(180.dp)

                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = state.nowPlaying?.results?.get(index)?.title ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = bodyFontFamily
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(R.drawable.ic_star),
                                contentDescription = null,
                                tint = CustomGold,

                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = state.nowPlaying?.results?.get(index)?.vote_average.toString(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun PopularSection(
    navController: NavController,
    state: HomeState
) {
    Column(
    ) {
        // top text row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(start = 20.dp)
                .height(500.dp)
        ) {

            itemsIndexed(state.nowPlaying?.results ?: emptyList()) { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        // TODO: can pass event to viewModel and catch it there to get rid of passing
                        //  viewModel to details screen and just update state through viewModel
                        .clickable { navController.navigate("details/${item.id}") }
                ) {
                    Row(
                        modifier = Modifier
                            .height(150.dp)
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(
                                    "https://image.tmdb.org/t/p/w500" + state.popular?.results?.get(
                                        index
                                    )?.poster_path
                                )
                                .addHeader("Authorization", "Bearer ${Constants.API_KEY}")
                                .crossfade(true)
                                .diskCachePolicy(CachePolicy.ENABLED)// it's the same even removing comments
                                .build(),
                            contentDescription = "poster image",
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)


                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                        ) {
                            Text(
                                text = state.popular?.results?.get(index)?.title ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = bodyFontFamily
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Icon(
                                    painter = painterResource(R.drawable.ic_star),
                                    contentDescription = null,
                                    tint = CustomGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = state.nowPlaying?.results?.get(index)?.vote_average.toString())
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            // TODO: reformat to pass as separate state
                            val movieGenresList = state.popular?.results?.get(index)?.genre_ids
                            val allGenres = state.genres?.genres
                            LazyRow(
                            ) {
                                items(movieGenresList.orEmpty()) { genreId ->
                                    val genreName = allGenres?.find { it.id == genreId }?.name
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
                        }
                    }
                }
            }
        }
    }
}













