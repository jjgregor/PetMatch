package com.api.service

import com.api.datamodel.AuthToken
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.POST

private const val CLIENT_ID = "client_it"
private const val CLIENT_SECRET = "client_secret"

interface PetFinderService {

    @POST("/token")
    fun getAccessToken(
        @Field(CLIENT_ID) id: String,
        @Field(CLIENT_SECRET) secret: String
    ): Observable<AuthToken>

}