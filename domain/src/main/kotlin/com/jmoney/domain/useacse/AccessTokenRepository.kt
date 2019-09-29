package com.jmoney.domain.useacse

import com.jmoney.domain.datamodel.AuthToken
import io.reactivex.Single

interface AccessTokenRepository {
    fun getToken(): Single<AuthToken>
}