package com.petmatch.api

import com.petmatch.domain.AccessTokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val AUTHORIZATION = "Authorization"
private const val BEARER = "Bearer "

class AuthenticationInterceptor @Inject constructor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getAccessToken()

        return if (token.isNullOrEmpty()) {
            chain.proceed(chain.request())
        } else {
            val authRequest = chain.request()
                .newBuilder()
                .addHeader(AUTHORIZATION, BEARER + token)
                .build()
            chain.proceed(authRequest)
        }
    }
}
