package dev.pan.movierecommender.presenter

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.pan.movierecommender.bodyFontFamily
import dev.pan.movierecommender.presenter.data.HomeState

@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState
) {
    Scaffold(
        topBar = {},
        bottomBar = {}
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            NowShowingSection(navController = navController, state = state)
            //PopularSection()

        }
    }

}

@Composable
fun NowShowingSection(modifier: Modifier = Modifier, navController: NavController, state: HomeState) {
    Column(
        modifier = Modifier.fillMaxSize()
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
                    .clickable { navController.navigate("details") }
                    .padding(8.dp),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // big image scroll
        LazyRow {
            items(state.nowPlaying?.results?.size ?: 0) { index ->
                Text(text = state.nowPlaying?.results?.get(index)?.title ?: "unknown")
//                Column {
//                    AsyncImage(
//                        model = state.nowPlaying?.results?.get(index)?.poster_path,
//                        contentDescription = null
//                    )
//                }
            }
        }
    }
}
















