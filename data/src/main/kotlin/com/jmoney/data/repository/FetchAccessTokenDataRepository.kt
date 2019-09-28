package com.jmoney.data.repository

import com.jmoney.api.service.PetFinderService
import com.jmoney.data.BuildConfig
import com.jmoney.data.adapter.AuthTokenAdapter
import com.jmoney.data.provider.AccessTokenDataProvider
import com.jmoney.domain.datamodel.AuthToken
import com.jmoney.domain.useacse.FetchAccessTokenRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchAccessTokenDataRepository @Inject constructor(
    private val petFinderService: PetFinderService,
    private val authTokenAdapter: AuthTokenAdapter,
    private val accessTokenDataRepository: AccessTokenDataProvider
) : FetchAccessTokenRepository {

    override fun invoke(): Single<AuthToken> {
        return petFinderService.getAccessToken(BuildConfig.API_KEY, BuildConfig.SECRET)
            .map { authTokenAdapter.adapt(it) }
            .doOnSuccess { accessTokenDataRepository.saveAccessToken(it) }

    }
}