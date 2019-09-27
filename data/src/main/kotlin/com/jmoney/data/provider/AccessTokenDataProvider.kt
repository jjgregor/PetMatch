package com.jmoney.data.provider

import com.jmoney.domain.provider.AccessTokenProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenDataProvider @Inject constructor() : AccessTokenProvider {

    private var token: String? = null

    override fun getAccessToken(): String? {
       return token
    }

    override fun saveAccessToken(token: String?) {
        this.token = token
    }
}
