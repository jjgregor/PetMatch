package com.petmatch.data

import android.content.SharedPreferences
import com.petmatch.domain.AccessTokenProvider
import javax.inject.Inject

private const val SHARED_PREFS_ACCESS_TOKEN_KEY = "shared-prefs-access-token-key"

class AccessTokenDataProvider @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AccessTokenProvider {

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(SHARED_PREFS_ACCESS_TOKEN_KEY, "")
    }

    override fun saveAccessToken(token: String?) {
        sharedPreferences.edit().putString(SHARED_PREFS_ACCESS_TOKEN_KEY, token).apply()
    }
}
