package com.petmatch.di

import com.petmatch.fragment.AuthFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(authFragment: AuthFragment)
}

interface AppComponentProvider {
    fun provideAppComponent(): AppComponent
}