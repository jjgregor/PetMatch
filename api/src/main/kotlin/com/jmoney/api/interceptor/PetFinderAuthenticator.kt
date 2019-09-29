package com.jmoney.api.interceptor

import android.util.Log
import com.jmoney.domain.datamodel.AuthToken
import com.jmoney.domain.useacse.FetchAccessToken
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

private const val AUTHORIZATION = "Authorization"
private const val BEARER = "Bearer "

class PetFinerAuthenticator @Inject constructor(
    private val fetchAccessToken: FetchAccessToken
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        var token: AuthToken? = null
        try {
            token = fetchAccessToken().blockingGet()
        } catch (e: Exception) {
            Log.e("PetFinderAuthenticator", "Error fetching access token")
        }

        if (token == null) {
            return null
        }

        return response.request()
            .newBuilder()
            .removeHeader(AUTHORIZATION)
            .addHeader(AUTHORIZATION, BEARER + token.accessToken)
            .build()
    }
}