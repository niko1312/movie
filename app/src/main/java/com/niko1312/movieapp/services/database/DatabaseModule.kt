package com.feel_it_services.ngen.managers.database

import androidx.room.Room
import com.niko1312.movieapp.MovieApp
import com.niko1312.movieapp.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @ApplicationScope
    @Provides
    internal fun getDb() =
        Room.databaseBuilder(
            MovieApp.getInstance(),
            MoviesDatabase::class.java, "movies"
        )
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    internal fun getMoviesDao(db: MoviesDatabase) = db.moviesDAO()

}