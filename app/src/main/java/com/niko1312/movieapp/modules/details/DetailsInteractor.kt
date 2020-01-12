package com.niko1312.movieapp.modules.details

import com.niko1312.movieapp.modules.main.models.MovieModel
import com.niko1312.movieapp.services.database.MoviesDao
import com.niko1312.movieapp.services.network.ApiService
import com.niko1312.movieapp.util.Prefs
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class DetailsInteractor(private var apiService: ApiService, private var moviesDao: MoviesDao) {

    fun fetchMoviesById(moviewId: Int): Single<MovieModel> =
        Single.zip(apiService.getMovieById(moviewId, Prefs.getKey()),
            moviesDao.getMovieById(moviewId),
            BiFunction<MovieModel, MovieModel, MovieModel> { remoteModel, cachedModel ->
                remoteModel.isFav = cachedModel.isFav
                remoteModel
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateMovie(movieId: Int, fav: Boolean) = Single.just(moviesDao)
        .map { it.updateMovie(movieId, fav) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}