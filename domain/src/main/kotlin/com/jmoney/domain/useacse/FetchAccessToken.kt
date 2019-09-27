package com.jmoney.domain.useacse

import android.annotation.SuppressLint
import android.util.Log
import com.jmoney.api.datamodel.AuthToken
import com.jmoney.api.service.PetFinderService
import com.jmoney.domain.BuildConfig
import com.petmatch.BuildConfig
import io.reactivex.Single
import javax.inject.Inject

class FetchAccessToken @Inject constructor(
    private val petFinderService: PetFinderService,
    private val tokenProvider: com.jmoney.domain.provider.AccessTokenProvider
) {

    @SuppressLint("CheckResult")
    operator fun invoke(): Single<AuthToken> {
        return petFinderService.getAccessToken(BuildConfig.API_KEY, BuildConfig.SECRET)
            .doOnSuccess { tokenProvider.saveAccessToken(it.accessToken) }
            .doOnError { Log.e("FetAuthToken", "Error fetching auth token") }
    }
}