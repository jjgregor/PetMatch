package com.petmatch.di

import android.app.Application
import com.jmoney.data.provider.AccessTokenDataProvider
import com.jmoney.data.repository.AccessTokenDataRepository
import com.jmoney.domain.provider.AccessTokenProvider
import com.jmoney.domain.useacse.AccessTokenRepository
import com.petmatch.common.AndroidSchedulers
import com.petmatch.interfaces.Schedulers
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module (includes = [AppModule.Declarations::class])
class AppModule(private val application: Application) {

    @Module
    interface Declarations {

        @Binds
        fun bindAccessTokenProvider(accessTokenDataProvider: AccessTokenDataProvider) : AccessTokenProvider

        @Binds
        fun bindAccessDataRepository(accessTokenDataRepository: AccessTokenDataRepository): AccessTokenRepository

        @Binds
        fun provideSchedulers(androidSchedulers: AndroidSchedulers) : Schedulers
    }

    @Provides
    @Singleton
    internal fun providesApplication() = application
}