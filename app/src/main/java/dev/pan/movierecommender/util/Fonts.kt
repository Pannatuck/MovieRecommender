package dev.pan.movierecommender.util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.pan.movierecommender.R

object Fonts {
    val ubuntoFamily = FontFamily(
        Font(R.font.ubuntu_regular, FontWeight.Normal),
        Font(R.font.ubuntu_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.ubuntu_bold, FontWeight.Bold)
    )

}