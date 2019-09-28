package com.jmoney.domain.useacse

import com.jmoney.domain.datamodel.AuthToken
import io.reactivex.Single

interface FetchAccessTokenRepository {
    operator fun invoke(): Single<AuthToken>
}