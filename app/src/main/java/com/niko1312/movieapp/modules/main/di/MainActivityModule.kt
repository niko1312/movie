package com.niko1312.movieapp.modules.main.di

import com.niko1312.movieapp.modules.main.MainActivity
import com.niko1312.movieapp.modules.main.MainInteractor
import com.niko1312.movieapp.modules.main.ProgressBarDialog
import com.niko1312.movieapp.services.database.MoviesDao
import com.niko1312.movieapp.services.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMainInteractor(
        apiService: ApiService,
        moviesDao: MoviesDao
    ) =
        MainInteractor(
            apiService, moviesDao
        )

    @Provides
    fun provideProgressBar(context: MainActivity) = ProgressBarDialog(context)
}