package com.android.moviesinformation.localMovies.movieList.info

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.moviesinformation.R
import com.android.moviesinformation.data.DataBaseHandler
import com.android.moviesinformation.data.response.MovieInfo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_local_movie_info.*

class LocalMovieInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_movie_info)

        val imdbID = intent.extras?.getString("imdbID")
        addDataSet(imdbID.toString(), this)

        add_button.setOnClickListener {

        }
    }

    private fun addDataSet(movieImdbID: String, context: Context) {
        val dataBase = DataBaseHandler(this)
        val data = dataBase.readSingleMovie(movieImdbID)
        val movieData = MovieInfo(
            data.title,
            data.poster,
            data.imdbID,
            data.released,
            data.runtime,
            data.genre,
            data.plot,
            data.imageByteArray
        )

        Glide.with(context)
            .load(movieData.imageByteArray)
            .placeholder(R.drawable.ic_launcher_background)
            .into(poster)

        movie_name.text = movieData.title
        plot.text = movieData.plot
        release.text = movieData.released
        runtime.text = movieData.runtime
        genre.text = movieData.genre
    }
}
