package dev.pan.movierecommender.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.pan.movierecommender.data.db.MovieDAO
import dev.pan.movierecommender.data.db.MovieDatabase
import dev.pan.movierecommender.data.network.ApiService
import dev.pan.movierecommender.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): ApiService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val request = original.newBuilder()
                            .header("accept", "application/json")
                            .header(
                                "Authorization",
                                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwYWNlZGU3ZjQ0OTBjNWNiODQ5ZWUxZmUwMjY1NDNmMyIsIm5iZiI6MTcxOTgyMzA3NC43NzU4MzQsInN1YiI6IjYxMGQ5MjE0YjRhNTQzMDAyYWNjN2FiNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1PEJ86AaCd_azkCfuo84LAe8-DKpRhir11czzmAkS-c"
                            )
                            .method(original.method, original.body)
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) : MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: MovieDatabase) : MovieDAO {
        return db.dao
    }
}