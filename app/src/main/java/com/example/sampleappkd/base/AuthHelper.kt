package com.example.sampleappkd.base

import com.example.sampleappkd.util.Constants.Companion.AUTH_TOKEN
import com.orhanobut.hawk.Hawk

object AuthHelper {

    private val authToken = Hawk.get<String>(AUTH_TOKEN)

    fun getAuthToken(accountName: String? = null): String? {
        return authToken
    }

}