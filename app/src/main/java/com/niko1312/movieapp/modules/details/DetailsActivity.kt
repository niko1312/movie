package com.niko1312.movieapp.modules.details

import android.os.Bundle
import com.bumptech.glide.Glide
import com.niko1312.movieapp.modules.main.ProgressBarDialog
import com.niko1312.movieapp.services.network.RetrofitBuilder
import com.niko1312.movieapp.util.Constants
import com.niko1312.movieapp.util.Constants.EXTRA_ID
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject


class DetailsActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var detailsActivityViewModel: DetailsActivityViewModel

    @Inject
    lateinit var progressBar: ProgressBarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.niko1312.movieapp.R.layout.activity_details)
        setSupportActionBar(toolbar)

        val movieId = intent.getIntExtra(EXTRA_ID, -1)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        progressBar.showProgress()
        detailsActivityViewModel.getMovieDetails(movieId) { model ->
            progressBar.dismissProgress()
            Glide.with(this)
                .load("${RetrofitBuilder.BASE_IMG_URL}/t/p/${Constants.IMG_SIZE}${model.poster}")
                .centerCrop()
                .into(movie_img)
            supportActionBar?.title = model.title
            movie_title.text = model.title
            movie_description.text = model.overview
            movie_rel_date.text = model.relDate
            fav.isChecked = model.isFav

        }

        fav.setOnClickListener { detailsActivityViewModel.updateMovieFav(1, false) }

    }
}