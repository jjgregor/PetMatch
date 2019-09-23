package com.petmatch.di

import android.app.Application
import com.petmatch.BuildConfig
import com.petmatch.api.AuthenticationInterceptor
import com.petmatch.api.PetFinderService
import com.petmatch.api.PetFinerAuthenticator
import com.petmatch.domain.AccessTokenProvider
import com.petmatch.useacse.FetchAccessToken
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

@Module
class NetworkModule {

    @Provides
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .build()
    }

    @Provides
    internal fun providePetFinderRestAdapter(
        tokenProvider: AccessTokenProvider,
        client: OkHttpClient,
        moshi: Moshi,
        fetchAccessToken: FetchAccessToken
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val authenticationInterceptor = AuthenticationInterceptor(tokenProvider)
        val petFinderAuthenticator = PetFinerAuthenticator(fetchAccessToken)

        val newClient = client.newBuilder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(petFinderAuthenticator)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(newClient)
            .build()
    }

    @Provides
    fun providePetFinderService(restAdapter: Retrofit) : PetFinderService {
        return restAdapter.create(PetFinderService::class.java)
    }

}
