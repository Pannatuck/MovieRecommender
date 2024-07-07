package dev.pan.movierecommender.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MovieEntity::class],
    version = 2
)
@TypeConverters(GenreConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract val dao: MovieDAO
}