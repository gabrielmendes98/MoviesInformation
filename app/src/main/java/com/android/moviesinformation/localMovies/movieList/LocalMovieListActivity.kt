package com.android.moviesinformation.localMovies.movieList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.moviesinformation.R
import com.android.moviesinformation.data.DataBaseHandler
import com.android.moviesinformation.data.response.MovieInfo
import kotlinx.android.synthetic.main.activity_searched_movies.*

class LocalMovieListActivity : AppCompatActivity() {

    lateinit var myMoviesAdapter: LocalMoviesAdapter
    private var movies = ArrayList<MovieInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searched_movies)

        initRecyclerView()
        addDataSet()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@LocalMovieListActivity)
            myMoviesAdapter = LocalMoviesAdapter()
            adapter = myMoviesAdapter
        }
    }

    private fun addDataSet() {
        val dataBase = DataBaseHandler(this)
        var data = dataBase.readData()
        for (i in 0 until data.size){
            val movieInfoDTO =
                MovieInfo(
                    data[i].title,
                    "",
                    data[i].imdbID,
                    data[i].released,
                    data[i].runtime,
                    data[i].genre,
                    data[i].plot,
                    data[i].imageByteArray
                )
            movies.add(movieInfoDTO)
        }
        myMoviesAdapter.submitList(movies)
    }
}
