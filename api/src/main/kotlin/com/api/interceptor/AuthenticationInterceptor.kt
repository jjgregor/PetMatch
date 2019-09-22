package com.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

private const val AUTHORIZATION = "Authorization"
class AuthenticationInterceptor : Interceptor {

    // TODO: get value from account manager or request a new one
    private val authToken = "token"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .header(AUTHORIZATION, authToken)

        val request = builder.build()
        return chain.proceed(request)
    }
}