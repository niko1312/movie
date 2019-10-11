package com.niko1312.movieapp.services.network

import com.niko1312.movieapp.modules.main.models.MovieListResponse
import com.niko1312.movieapp.modules.main.models.MovieModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular")
    fun getPopularMoviesList(@Query("api_key") apiKey: String): Single<MovieListResponse>

    @GET("/3/movie/{movieId}")
    fun getMovieById(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String): Single<MovieModel>
}