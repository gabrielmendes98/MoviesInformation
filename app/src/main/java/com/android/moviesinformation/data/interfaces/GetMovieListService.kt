package com.android.moviesinformation.data.interfaces

import com.android.moviesinformation.data.response.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetMovieListService {
    @GET("?apikey=4393e391&type=movie&")
    fun getAllMovies(
        @Query("s") Title: String
    ): Call<MovieList>
}