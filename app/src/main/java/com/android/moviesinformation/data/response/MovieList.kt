package com.android.moviesinformation.data.response

import com.google.gson.annotations.SerializedName

data class MovieList(@SerializedName("Search") var Search: List<MovieInfo>) {
}