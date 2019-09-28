package com.jmoney.data.provider

import com.jmoney.domain.datamodel.AuthToken
import com.jmoney.domain.provider.AccessTokenProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenDataProvider @Inject constructor() : AccessTokenProvider {

    private var token: AuthToken? = null

    override fun getAccessToken(): String? {
       return token?.accessToken
    }

    override fun saveAccessToken(token: AuthToken) {
        this.token = token
    }
}
