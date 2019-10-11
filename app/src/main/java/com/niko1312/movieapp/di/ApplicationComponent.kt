package com.niko1312.movieapp.di

import com.feel_it_services.ngen.managers.database.DatabaseModule
import com.niko1312.movieapp.MovieApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(
    modules = [AndroidInjectionModule::class,
        ViewModelModule::class,
        ApplicationModule::class,
        DatabaseModule::class,
        ActivityBindingModule::class]
)

interface ApplicationComponent : AndroidInjector<MovieApp> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: MovieApp
        ): ApplicationComponent
    }

}