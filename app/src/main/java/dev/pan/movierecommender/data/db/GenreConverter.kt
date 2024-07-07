package dev.pan.movierecommender.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.pan.movierecommender.data.network.models.details.Genre

class GenreConverter {
    @TypeConverter
    fun fromGenreList(genreList: List<Genre>?) : String? {
        if (genreList == null) {return null}
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.toJson(genreList, type)
    }

    @TypeConverter
    fun toGenreList(genreListJson: String?) : List<Genre>? {
        if (genreListJson == null) {return null}

        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genreListJson, type)
    }

}