package dev.pan.movierecommender.presenter

import android.os.Parcelable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComplexData(
    val title: String,
    val description: String
) : Parcelable

@Composable
fun DetailsScreen(
    navController: NavController
) {
    Text(text = "Details screen")
}