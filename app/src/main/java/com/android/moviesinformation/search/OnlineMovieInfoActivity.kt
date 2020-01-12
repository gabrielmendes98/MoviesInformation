package com.android.moviesinformation.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.moviesinformation.R
import kotlinx.android.synthetic.main.activity_search_movie.*

class OnlineMovieInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        search_button.setOnClickListener {
            openMyMoviesActivity()
        }
    }

    private fun openMyMoviesActivity(){
        val intent = Intent(this, OnlineMoviesActivity:: class.java)
        intent.putExtra("Movie_Name", textInputLayout2.text.toString())
        startActivity(intent)
        finish()
    }

}
