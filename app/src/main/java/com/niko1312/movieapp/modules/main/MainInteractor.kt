package com.niko1312.movieapp.modules.main

import com.niko1312.movieapp.modules.main.models.MovieModel
import com.niko1312.movieapp.services.database.MoviesDao
import com.niko1312.movieapp.services.network.ApiService
import com.niko1312.movieapp.util.Prefs
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainInteractor(private var apiService: ApiService, private var moviesDao: MoviesDao) {

    fun fetchMoviesFromRemote(): Single<List<MovieModel>> {
        return apiService.getPopularMoviesList(Prefs.getKey()).map { response ->
            response.results
        }.onErrorReturn { arrayListOf() }
            .doOnSuccess { list -> moviesDao.insertMovies(list) }
            .flatMap { getMoviesFromCache() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getMoviesFromCache() =
        moviesDao.getAllMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateMovieFav(movieId: Int, isFav: Boolean) =
        Single.just(moviesDao)
            .map { dao -> dao.updateMovie(movieId, isFav) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}