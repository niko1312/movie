package com.niko1312.movieapp.di

import com.niko1312.movieapp.services.network.ApiService
import com.niko1312.movieapp.services.network.RetrofitBuilder
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @ApplicationScope
    @Provides
    internal fun provideRetrofitClient() = RetrofitBuilder.createService(ApiService::class.java)

}