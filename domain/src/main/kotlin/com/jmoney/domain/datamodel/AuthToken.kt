package com.jmoney.domain.datamodel

data class AuthToken(
    val tokenType: String,
    val expiresIn: Int,
    val accessToken: String
)