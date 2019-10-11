package com.niko1312.movieapp.modules.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.niko1312.movieapp.R
import com.niko1312.movieapp.modules.details.DetailsActivity
import com.niko1312.movieapp.util.Constants.EXTRA_ID
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val moviesListAdapter = MoviesListAdapter()

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    @Inject
    lateinit var progressBar: ProgressBarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rv_movies.adapter = moviesListAdapter
        rv_movies.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        fab.setOnClickListener { fetchData() }
        fetchData()
    }

    private fun fetchData() {
        progressBar.showProgress()
        mainActivityViewModel.getMoviesList { list ->
            progressBar.dismissProgress()
            moviesListAdapter.setItems(list)
            moviesListAdapter.addOnClickListener {
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(EXTRA_ID, it)
                startActivity(intent)
            }
            moviesListAdapter.addOnFavListener {
                mainActivityViewModel.updateMovieFav(it)
            }
        }
    }

}
