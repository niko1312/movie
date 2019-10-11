package com.niko1312.movieapp.di

import com.niko1312.movieapp.modules.details.DetailsActivity
import com.niko1312.movieapp.modules.details.di.DetailsActivityModule
import com.niko1312.movieapp.modules.details.di.DetailsActivityScope
import com.niko1312.movieapp.modules.main.MainActivity
import com.niko1312.movieapp.modules.main.di.MainActivityModule
import com.niko1312.movieapp.modules.main.di.MainActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @DetailsActivityScope
    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    internal abstract fun bindDetailsActivity(): DetailsActivity
}
