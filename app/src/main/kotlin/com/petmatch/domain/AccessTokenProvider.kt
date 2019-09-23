package com.petmatch.domain

interface AccessTokenProvider {
    fun getAccessToken(): String?
    fun saveAccessToken(token: String?)
}