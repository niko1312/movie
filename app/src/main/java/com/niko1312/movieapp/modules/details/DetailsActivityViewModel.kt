package com.niko1312.movieapp.modules.details

import androidx.lifecycle.ViewModel
import com.niko1312.movieapp.modules.main.models.MovieModel
import com.niko1312.movieapp.util.ConsumerFunc
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsActivityViewModel @Inject constructor(
    private var interactor: DetailsInteractor
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetails(movieId: Int, function: ConsumerFunc<MovieModel>) {
        compositeDisposable.add(interactor.fetchMoviesById(movieId)
            .doOnSuccess { function(it) }
            .subscribe({}, {})
        )
    }

    fun updateMovieFav(movieId: Int, isFav: Boolean) {
        compositeDisposable.add(
            interactor.updateMovie(movieId, isFav)
                .subscribe()
        )
    }

}