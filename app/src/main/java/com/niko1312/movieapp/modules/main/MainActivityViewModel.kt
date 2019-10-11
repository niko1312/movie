package com.niko1312.movieapp.modules.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.niko1312.movieapp.modules.main.models.MovieModel
import com.niko1312.movieapp.util.ConsumerFunc
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private var interactor: MainInteractor
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getMoviesList(consumerFunc: ConsumerFunc<List<MovieModel>>) {
        compositeDisposable.add(interactor.fetchMoviesFromRemote()
            .doOnSuccess { consumerFunc(it) }
            .subscribe({}, {
                Log.e("-->", "error retrieving movie list")
            })
        )
    }

    fun updateMovieFav(model: Pair<Int, Boolean>) {
        compositeDisposable.add(
            interactor.updateMovieFav(model.first, model.second)
                .subscribe({}, {
                    Log.e("-->", "error updating movie")
                })
        )
    }
}