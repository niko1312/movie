package com.niko1312.movieapp.modules.details.di

import com.niko1312.movieapp.modules.details.DetailsActivity
import com.niko1312.movieapp.modules.details.DetailsInteractor
import com.niko1312.movieapp.modules.main.ProgressBarDialog
import com.niko1312.movieapp.services.database.MoviesDao
import com.niko1312.movieapp.services.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class DetailsActivityModule {

    @Provides
    internal fun provideDetailsInteractor(
        apiService: ApiService,
        moviesDao: MoviesDao
    ) =
        DetailsInteractor(
            apiService, moviesDao
        )

    @DetailsActivityScope
    @Provides
    fun provideProgressBar(context: DetailsActivity) = ProgressBarDialog(context)
}