package dev.pan.movierecommender.presenter

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun FavoritesScreen(
    navController: NavController
) {
    Text(text = "Favorites")
}