package dev.pan.movierecommender.presenter

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.TypedArrayUtils.getResourceId
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.pan.movierecommender.CustomGold
import dev.pan.movierecommender.R
import dev.pan.movierecommender.bodyFontFamily
import dev.pan.movierecommender.data.db.MovieEntity
import dev.pan.movierecommender.presenter.data.HomeState
import dev.pan.movierecommender.util.Constants

@Composable
fun FavoritesScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    Scaffold { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
        ) {
            itemsIndexed(viewModel.movies.value) { _, movie ->
                MovieItem(movie = movie, navController = navController)
            }
        }
    }


}

@Composable
fun MovieItem(
    movie: MovieEntity,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("details/${movie.movieId}") }
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    // TODO: temp
                    "https://image.tmdb.org/t/p/w500" + movie.backdropPath
                )
                .addHeader("Authorization", "Bearer ${Constants.API_KEY}")
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)// it's the same even removing comments
                .build(),
            contentDescription = "poster image",
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)

        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Gray.copy(alpha = 0.5f))
                .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)

        ) {
            Text(
                text = movie.title,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = bodyFontFamily,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    tint = CustomGold,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = movie.voteAverage.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }
        }

    }
}