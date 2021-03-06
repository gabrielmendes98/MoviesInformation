package com.android.moviesinformation.localMovies.movieList

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.moviesinformation.R
import com.android.moviesinformation.data.response.MovieInfo
import com.android.moviesinformation.localMovies.movieList.info.LocalMovieInfoActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_list_item.view.*

class LocalMoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<MovieInfo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MovieViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun submitList(movieInfoList: List<MovieInfo>){
        items = movieInfoList
        notifyDataSetChanged()
    }

    class MovieViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val poster: CircleImageView = itemView.image
        val title: TextView = itemView.name

        fun bind(movieInfo: MovieInfo){
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(movieInfo.imageByteArray)
                .into(poster)
            title.text = movieInfo.title
            val teste = movieInfo.imdbID

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, LocalMovieInfoActivity::class.java)
                intent.putExtra("imdbID", movieInfo.imdbID)
                itemView.context.startActivity(intent)
//                Toast.makeText(itemView.context, name.text, Toast.LENGTH_LONG).show()
            }
        }

    }
}