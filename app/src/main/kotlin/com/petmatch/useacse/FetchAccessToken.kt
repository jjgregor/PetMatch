package com.petmatch.useacse

import android.annotation.SuppressLint
import android.util.Log
import com.petmatch.BuildConfig
import com.petmatch.api.AuthToken
import com.petmatch.api.PetFinderService
import com.petmatch.domain.AccessTokenProvider
import io.reactivex.Single
import javax.inject.Inject

class FetchAccessToken @Inject constructor(
    private val petFinderService: PetFinderService,
    private val tokenProvider: AccessTokenProvider
) {

    @SuppressLint("CheckResult")
    operator fun invoke(): Single<AuthToken> {
        return petFinderService.getAccessToken(BuildConfig.API_KEY, BuildConfig.SECRET)
            .doOnSuccess { tokenProvider.saveAccessToken(it.accessToken) }
            .doOnError { Log.e("FetAuthToken", "Error fetching auth token") }
    }
}