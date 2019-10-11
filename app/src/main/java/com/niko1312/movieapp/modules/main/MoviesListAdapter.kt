package com.niko1312.movieapp.modules.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niko1312.movieapp.R
import com.niko1312.movieapp.modules.main.models.MovieModel
import com.niko1312.movieapp.services.network.RetrofitBuilder.BASE_IMG_URL
import com.niko1312.movieapp.util.Constants.IMG_SIZE
import com.niko1312.movieapp.util.ConsumerFunc

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.MoviesVH>() {
    private lateinit var consumerFunc: ConsumerFunc<Int>
    private lateinit var favListener: ConsumerFunc<Pair<Int, Boolean>>

    private var listMovies = listOf<MovieModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoviesVH(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_movie_item,
            parent, false
        )
    )

    override fun getItemCount() = listMovies.size

    override fun onBindViewHolder(holder: MoviesVH, position: Int) {
        holder.bind(listMovies[position], consumerFunc, favListener)
    }

    fun setItems(listMovies: List<MovieModel>) {
        this.listMovies = listMovies
        notifyDataSetChanged()
    }

    fun addOnClickListener(consumerFunc: ConsumerFunc<Int>) {
        this.consumerFunc = consumerFunc
    }

    fun addOnFavListener(consumerFunc: ConsumerFunc<Pair<Int, Boolean>>) {
        this.favListener = consumerFunc
    }

    class MoviesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTitle: TextView = itemView.findViewById(R.id.movie_title)
        private var tvDescription: TextView = itemView.findViewById(R.id.movie_description)
        private var tvRelDate: TextView = itemView.findViewById(R.id.movie_rel_date)
        private var ivBanner: ImageView = itemView.findViewById(R.id.movie_img)
        private var ivFav: CheckBox = itemView.findViewById(R.id.fav)

        fun bind(
            model: MovieModel,
            itemListener: ConsumerFunc<Int>,
            favListener: ConsumerFunc<Pair<Int, Boolean>>
        ) {
            Glide.with(ivBanner.context)
                .load("${BASE_IMG_URL}/t/p/${IMG_SIZE}${model.poster}")
                .centerCrop()
                .into(ivBanner)
            tvTitle.text = model.title
            tvDescription.text = model.overview
            tvRelDate.text = model.relDate
            ivFav.isChecked = model.isFav
            ivFav.setOnClickListener { favListener(Pair(model.id, ivFav.isChecked)) }
            itemView.setOnClickListener { itemListener(model.id) }
        }
    }
}