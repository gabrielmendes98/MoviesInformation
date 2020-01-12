package com.android.moviesinformation.localMovies.movieList.info

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.moviesinformation.data.DataBaseHandler
import com.android.moviesinformation.data.interfaces.GetSpecificMovieService
import com.android.moviesinformation.data.RetrofitClientInstance
import com.android.moviesinformation.data.response.MovieInfo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_movie_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.android.moviesinformation.R
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class MovieInfoActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var movieData: MovieInfo
    private val context = this
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_movie_info)

        val imdbID = intent.extras?.getString("imdbID")
        addDataSet(imdbID.toString(), this)

        add_button.setOnClickListener {
            launch {
                val byteArray = catchByteArray()
                val dataBase = DataBaseHandler(context)
                if (byteArray != null) {
                    dataBase.insertData(movieData, byteArray)
                }
            }
        }
    }

    private suspend fun catchByteArray(): ByteArray?{
        val byteArray: ByteArray
        try {
            byteArray = withContext(Dispatchers.IO) {
                return@withContext Glide.with(context)
                    .`as`(ByteArray::class.java)
                    .load(movieData.poster)
                    .submit()
                    .get()
            }
        } catch(e: Exception) {
            val str = e.toString()
            Toast.makeText(this, str, Toast.LENGTH_LONG)
            return null
        }
        return byteArray
    }

    private fun addDataSet(movieImdbID: String, context: Context) {
        val service = RetrofitClientInstance.retrofitInstance?.create(GetSpecificMovieService::class.java)
        val call = service?.getMovie(movieImdbID)
        call?.enqueue(object : Callback<MovieInfo> {
            override fun onResponse(call: Call<MovieInfo>?, response: Response<MovieInfo>) {
                val body = response.body()
                movieData =
                    MovieInfo(
                        body!!.title,
                        body.poster,
                        body.imdbID,
                        body.released,
                        body.runtime,
                        body.genre,
                        body.plot
                    )

                Glide.with(context)
                    .load(movieData.poster)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(poster)

                movie_name.text = movieData.title
                plot.text = movieData.plot
                release.text = movieData.released
                runtime.text = movieData.runtime
                genre.text = movieData.genre
                plot.text = movieData.plot
            }

            override fun onFailure(call: Call<MovieInfo>?, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

}
