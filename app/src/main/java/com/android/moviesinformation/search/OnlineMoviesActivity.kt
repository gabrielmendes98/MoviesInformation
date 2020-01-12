package com.android.moviesinformation.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.moviesinformation.R
import com.android.moviesinformation.data.interfaces.GetMovieListService
import com.android.moviesinformation.data.RetrofitClientInstance
import com.android.moviesinformation.data.response.MovieInfo
import com.android.moviesinformation.data.response.MovieList
import kotlinx.android.synthetic.main.activity_searched_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnlineMoviesActivity : AppCompatActivity(){

    private val TAG = "MainActivity"

    private lateinit var onlineAdapter: OnlineRecyclerViewAdapter
    private var movies: List<MovieInfo>? = null
    val movieData = ArrayList<MovieInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searched_movies)
        Log.d(TAG, "onCreate: started.")

        val movieName = intent.extras?.getString("Movie_Name")

        initRecyclerView()
        addDataSet(movieName.toString())
    }

    private fun initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recycler view.")
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@OnlineMoviesActivity)
            onlineAdapter = OnlineRecyclerViewAdapter()
            adapter = onlineAdapter
        }
    }

    private fun addDataSet(movieName: String) {
        val service = RetrofitClientInstance.retrofitInstance?.create(GetMovieListService::class.java)
        val call = service?.getAllMovies(movieName)
        call?.enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>?, response: Response<MovieList>?) {
                val body = response?.body()
                movies = body?.Search

                movies?.forEach { movie ->
                    movieData.add(
                        MovieInfo(
                            title = movie.title,
                            poster = movie.poster,
                            imdbID = movie.imdbID
                        )
                    )
                }

                onlineAdapter.submitList(movieData)
            }

            override fun onFailure(call: Call<MovieList>?, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}