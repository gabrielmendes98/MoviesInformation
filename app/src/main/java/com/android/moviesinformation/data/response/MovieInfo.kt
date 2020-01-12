package com.android.moviesinformation.data.response

import com.google.gson.annotations.SerializedName

data class MovieInfo (
    @SerializedName("Title") var title:String = "default",
    @SerializedName("Poster") var poster: String = "default",
    @SerializedName("imdbID") var imdbID: String = "default",
    @SerializedName("Released") var released: String = "default",
    @SerializedName("Runtime") var runtime: String = "default",
    @SerializedName("Genre") var genre: String = "default",
    @SerializedName("Plot") var plot: String = "default",
    var imageByteArray: ByteArray = ByteArray(0)
)