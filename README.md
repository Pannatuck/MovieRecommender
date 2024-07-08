# Movie Recommender

Small project that displays currently streamed movies and movies that popular among the viewers.

It uses Compose, Room, Retrofit, Paging, Coil, Hilt and Coroutines in it.

[TMDB](https://developer.themoviedb.org/) is used as API resource for this project 

**Examples of how program work:**
  [1.webm](https://github.com/Pannatuck/MovieRecommender/assets/25908757/5a95b30d-e9e3-4eb0-925a-a2b631d4abff)
  [2.webm](https://github.com/Pannatuck/MovieRecommender/assets/25908757/6b55a41f-80e1-4f14-8ffc-fb48c14e6f0b)
  [3.webm](https://github.com/Pannatuck/MovieRecommender/assets/25908757/b93cc09c-002b-4625-802a-056991880c7a)

This project doesn't have good architecture, I just used it to play around with stuff. But still, it follows MVVM approach.

## How to use this project

Project must be full ready to work from scratch, you just need to build it. In case you want to use your own key, change it in `Constants.kt`:

```Kotlin
const val API_KEY = "YOUR_BEARER_KEY"
```

