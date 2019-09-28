package com.jmoney.data.adapter

import com.jmoney.api.datamodel.ApiAuthToken
import com.jmoney.domain.datamodel.AuthToken
import javax.inject.Inject

class AuthTokenAdapter @Inject constructor() {

    fun adapt(apiAuthToken: ApiAuthToken): AuthToken {
        return AuthToken(
            tokenType = checkNotNull(apiAuthToken.tokenType) { "Token type cannot be null!" },
            accessToken = checkNotNull(apiAuthToken.accessToken) { "Access Token cannot be null!" },
            expiresIn = apiAuthToken.expiresIn ?: 0
        )
    }
}