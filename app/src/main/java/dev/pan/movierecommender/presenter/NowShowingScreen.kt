@file:OptIn(ExperimentalMaterial3Api::class)

package dev.pan.movierecommender.presenter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.pan.movierecommender.CustomGold
import dev.pan.movierecommender.R
import dev.pan.movierecommender.bodyFontFamily
import dev.pan.movierecommender.data.db.MovieEntity
import dev.pan.movierecommender.util.Constants

@Composable
fun NowShowingScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val lazyPagingItems = viewModel.nowPlayingPagingFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Now Showing") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
        ) {
            items(lazyPagingItems.itemCount) { index ->
                val item = lazyPagingItems[index]
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { navController.navigate("details/${item?.id}") }

                ) {
                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(
                                    "https://image.tmdb.org/t/p/w500" + item?.poster_path
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
                            text = item?.title ?: "",
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
                                text = item?.vote_average.toString(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}