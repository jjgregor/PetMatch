package com.petmatch

import android.app.Application
import com.petmatch.di.AppComponent
import com.petmatch.di.AppModule
import com.petmatch.di.DaggerAppComponent
import com.petmatch.di.NetworkModule

class PetMatchApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    fun getPetMatchComponent() = appComponent
}