package com.niko1312.movieapp.services.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niko1312.movieapp.modules.main.models.MovieModel
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieModel")
    fun getAllMovies(): Single<List<MovieModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(areaList: ArrayList<MovieModel>)

    @Query("UPDATE MovieModel SET isFav = :isFav WHERE id = :movieId")
    fun updateMovie(movieId: Int, isFav: Boolean)

    @Query("SELECT * FROM MovieModel WHERE id = :movieId")
    fun getMovieById(movieId: Int): Single<MovieModel>

}