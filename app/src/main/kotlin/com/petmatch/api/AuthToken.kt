package com.petmatch.api

data class AuthToken(
    val tokenType: String? = null,
    val expiresIn: Int? = null,
    val accessToken: String? = null
)