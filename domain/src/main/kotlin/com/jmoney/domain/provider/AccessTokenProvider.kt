package com.jmoney.domain.provider

interface AccessTokenProvider {
    fun getAccessToken(): String?
    fun saveAccessToken(token: String?)
}