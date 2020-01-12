package com.android.moviesinformation.data.interfaces

import com.android.moviesinformation.data.response.MovieInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSpecificMovieService {
    @GET("?apikey=4393e391&")
    fun getMovie(
        @Query("i") imdbID: String
    ): Call<MovieInfo>
}