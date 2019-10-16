package com.petmatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmoney.domain.useacse.FetchAccessToken
import com.petmatch.interfaces.Schedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val fetchAccessToken: FetchAccessToken,
    private val schedulers: Schedulers
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _accessTokenLiveData = MutableLiveData<Boolean>()
    val accessTokenLiveData: LiveData<Boolean> = _accessTokenLiveData

    init {
        getAccessToken()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun getAccessToken() {
        fetchAccessToken()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(
                onSuccess = { _accessTokenLiveData.postValue(it.accessToken.isNotEmpty()) },
                onError = { Timber.e("Error fetching access token") }
            ).addTo(compositeDisposable)
    }
}