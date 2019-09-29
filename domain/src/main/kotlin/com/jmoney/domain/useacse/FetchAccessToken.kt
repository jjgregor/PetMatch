package com.jmoney.domain.useacse

import com.jmoney.domain.datamodel.AuthToken
import io.reactivex.Single
import javax.inject.Inject

class FetchAccessToken @Inject constructor(
    private val accessTokenRepository: AccessTokenRepository
) {

    operator fun invoke(): Single<AuthToken> {
        return accessTokenRepository.getToken()
    }
}