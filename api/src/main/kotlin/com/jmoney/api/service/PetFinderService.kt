package com.jmoney.api.service

import com.jmoney.api.datamodel.ApiAuthToken
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private const val CLIENT_ID = "client_it"
private const val CLIENT_SECRET = "client_secret"

interface PetFinderService {

    @FormUrlEncoded
    @POST("/v2/oauth2/token")
    fun getAccessToken(
        @Field(CLIENT_ID) id: String,
        @Field(CLIENT_SECRET) secret: String
    ): Single<ApiAuthToken>

}