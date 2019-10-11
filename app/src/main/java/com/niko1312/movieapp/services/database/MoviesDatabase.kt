package com.feel_it_services.ngen.managers.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.niko1312.movieapp.modules.main.models.MovieModel
import com.niko1312.movieapp.services.database.MoviesDao

@Database(
    entities = [MovieModel::class], version = 1
)
abstract class MoviesDatabase() : RoomDatabase() {
    abstract fun moviesDAO(): MoviesDao
}
