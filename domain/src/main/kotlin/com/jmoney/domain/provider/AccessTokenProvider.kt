package com.jmoney.domain.provider

import com.jmoney.domain.datamodel.AuthToken

interface AccessTokenProvider {
    fun getAccessToken(): String?
    fun saveAccessToken(token: AuthToken)
}