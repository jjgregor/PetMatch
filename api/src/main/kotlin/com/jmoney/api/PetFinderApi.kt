package com.jmoney.api

import com.jmoney.api.datamodel.ApiAuthToken
import com.jmoney.api.interceptor.AuthenticationInterceptor
import com.jmoney.api.interceptor.PetFinderAuthenticator
import com.jmoney.api.service.PetFinderService
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class PetFinderApi private constructor(
    private val petFinderService: PetFinderService
) : PetFinderService {

    class Builder(
        private val retrofitBuilder: Retrofit.Builder
    ) {
        private lateinit var okHttpClient: OkHttpClient
        private lateinit var moshi: Moshi
        private lateinit var petFinderAuthenticator: PetFinderAuthenticator
        private lateinit var authenticationInterceptor: AuthenticationInterceptor
        private var interceptors: MutableSet<Interceptor> = mutableSetOf()
        fun okHttpClient(client: OkHttpClient): Builder {
            okHttpClient = client
            return this
        }

        fun authenticationInterceptor(interceptor: AuthenticationInterceptor): Builder {
            authenticationInterceptor = interceptor
            interceptors.add(authenticationInterceptor)
            return this
        }

        fun rxJava2CallAdapterFactory(factory: RxJava2CallAdapterFactory): Builder {
            retrofitBuilder.addCallAdapterFactory(factory)
            return this
        }

        fun petFinderAuthenticator(authenticator: PetFinderAuthenticator): Builder {
            this.petFinderAuthenticator = authenticator
            return this
        }

        fun moshi(moshi: Moshi): Builder {
            this.moshi = moshi
            retrofitBuilder.addConverterFactory(MoshiConverterFactory.create(moshi))
            return this
        }

        fun build(): PetFinderApi {
            if(BuildConfig.DEBUG ){
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
                interceptors.add(loggingInterceptor)
            }

            okHttpClient = okHttpClient.newBuilder()
                .apply {
                    interceptors().addAll(interceptors)
                    authenticator(petFinderAuthenticator)}
                .build()

           val retrofitBuilder = retrofitBuilder
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

            val petFinderService = retrofitBuilder.create(PetFinderService::class.java)
            return PetFinderApi(
                petFinderService = petFinderService
            )
        }
    }

    override fun getAccessToken(id: String, secret: String): Single<ApiAuthToken> {
        return petFinderService.getAccessToken(id, secret)
    }

}