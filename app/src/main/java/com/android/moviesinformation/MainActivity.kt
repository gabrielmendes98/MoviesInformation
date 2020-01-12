package com.android.moviesinformation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.moviesinformation.data.DataBaseHandler
import com.android.moviesinformation.localMovies.movieList.LocalMovieListActivity
import com.android.moviesinformation.search.OnlineMovieInfoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        my_movies_button.setOnClickListener{
            openLocalMoviesActivity()
        }

        add_movies_button.setOnClickListener{
            openSearchMovieActivity()
        }

        reset_data_base.setOnClickListener {
            val dataBase = DataBaseHandler(this)
            dataBase.dropTable()
        }

    }

    private fun openLocalMoviesActivity(){
        val intent = Intent(this, LocalMovieListActivity:: class.java)
        startActivity(intent)
    }

    private fun openSearchMovieActivity(){
        val intent = Intent(this, OnlineMovieInfoActivity:: class.java)
        startActivity(intent)
    }

}
