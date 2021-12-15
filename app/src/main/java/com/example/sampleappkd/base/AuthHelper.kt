package com.example.sampleappkd.base

import com.example.sampleappkd.util.Constants.Companion.AUTH_TOKEN
import com.orhanobut.hawk.Hawk

object AuthHelper {

    private val authToken = Hawk.get<String>(AUTH_TOKEN)

    fun saveAuthToken(token: String) {
        Hawk.put(AUTH_TOKEN, token)
    }

    fun getAuthToken(): String? {
        return authToken
    }

    fun removeAuthToken() {
        Hawk.delete(AUTH_TOKEN)
    }

}