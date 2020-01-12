package com.android.moviesinformation.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.android.moviesinformation.data.response.MovieInfo

const val DATABASE_NAME = "MoviesDB"
const val TABLE_NAME = "Movies"
const val COL_ID = "id"
const val COL_TITLE = "title"
const val COL_IMDBID = "imdbID"
const val COL_RELEASED = "released"
const val COL_RUNTIME = "runtime"
const val COL_GENRE = "genre"
const val COL_PLOT = "plot"
const val COL_IMG = "image"

class DataBaseHandler(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_IMDBID VARCHAR(255), $COL_TITLE VARCHAR(255), $COL_RELEASED VARCHAR(255), " +
                "$COL_RUNTIME VARCHAR(255), $COL_GENRE VARCHAR(255), $COL_PLOT VARCHAR(255), " +
                "$COL_IMG BLOB)"
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(movie: MovieInfo, img: ByteArray){
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_IMDBID, movie.imdbID)
        contentValues.put(COL_TITLE, movie.title)
        contentValues.put(COL_RELEASED, movie.released)
        contentValues.put(COL_RUNTIME, movie.runtime)
        contentValues.put(COL_GENRE, movie.genre)
        contentValues.put(COL_PLOT, movie.plot)
        contentValues.put(COL_IMG, img)

        val result = dataBase.insert(TABLE_NAME, null, contentValues)
        if(result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()

        dataBase.close()

    }

    fun readData() : ArrayList<MovieInfo>{
        val movieList = ArrayList<MovieInfo>()

        val dataBase = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = dataBase.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                val movie =
                    MovieInfo()
                movie.title = result.getString(result.getColumnIndex(COL_TITLE))
                movie.genre = result.getString(result.getColumnIndex(COL_GENRE))
                movie.plot = result.getString(result.getColumnIndex(COL_PLOT))
                movie.released = result.getString(result.getColumnIndex(COL_RELEASED))
                movie.runtime = result.getString(result.getColumnIndex(COL_RUNTIME))
                movie.imageByteArray = result.getBlob(result.getColumnIndex(COL_IMG))
                movie.imdbID = result.getString(result.getColumnIndex(COL_IMDBID))
                movieList.add(movie)
            } while (result.moveToNext())
        }

        result.close()
        dataBase.close()

        return movieList
    }

    fun readSingleMovie(imdbID: String) : MovieInfo {
        val movie = MovieInfo()
        val database = this.readableDatabase
        val query = "Select * from $TABLE_NAME where $COL_IMDBID = '$imdbID';"
        val result = database.rawQuery(query, null)
        if(result.moveToFirst()){
            movie.title = result.getString(result.getColumnIndex(COL_TITLE))
            movie.genre = result.getString(result.getColumnIndex(COL_GENRE))
            movie.plot = result.getString(result.getColumnIndex(COL_PLOT))
            movie.released = result.getString(result.getColumnIndex(COL_RELEASED))
            movie.runtime = result.getString(result.getColumnIndex(COL_RUNTIME))
            movie.imageByteArray = result.getBlob(result.getColumnIndex(COL_IMG))
            movie.imdbID = result.getString(result.getColumnIndex(COL_IMDBID))
        }

        return movie
    }

    fun dropTable(){
        val dataBase = this.writableDatabase
        dataBase?.execSQL("delete from $TABLE_NAME")
        dataBase.close()
    }

}