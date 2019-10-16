package com.petmatch.di

import android.app.Application
import com.jmoney.api.BuildConfig
import com.jmoney.api.PetFinderApi
import com.jmoney.api.interceptor.AuthenticationInterceptor
import com.jmoney.api.interceptor.PetFinderAuthenticator
import com.jmoney.api.service.PetFinderService
import com.jmoney.domain.useacse.FetchAccessToken
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIME_OUT = 30L

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providePetFinderApi(
        builder: PetFinderApi.Builder,
        moshi: Moshi,
        factory: RxJava2CallAdapterFactory,
        interceptor: AuthenticationInterceptor,
        authenticator: PetFinderAuthenticator,
        okHttpClient: OkHttpClient
    ): PetFinderApi {
        return builder
            .moshi(moshi)
            .authenticationInterceptor(interceptor)
            .petFinderAuthenticator(authenticator)
            .rxJava2CallAdapterFactory(factory)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun providePetFinderService(restAdapter: Retrofit) : PetFinderService {
        return restAdapter.create(PetFinderService::class.java)
    }

    @Provides
    fun provideRetroFitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Provides
    fun providePetFinderAuthenticator(
        fetchAccessToken: FetchAccessToken
    ): PetFinderAuthenticator {
        return PetFinderAuthenticator(fetchAccessToken)
    }
}
