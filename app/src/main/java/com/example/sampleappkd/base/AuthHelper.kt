package com.example.sampleappkd.base

import android.content.Context
import com.example.sampleappkd.util.Constants.Companion.AUTH_TOKEN
import com.example.sampleappkd.util.PreferenceHelper

object AuthHelper {

    lateinit var sharedPref: PreferenceHelper

    fun saveAuthToken(context: Context, token: String) {
        sharedPref = PreferenceHelper(context)
        sharedPref.put(AUTH_TOKEN, token)
    }

    fun getAuthToken(context: Context): String? {
        sharedPref = PreferenceHelper(context)
        return sharedPref.getString(AUTH_TOKEN)
    }

    fun removeAuthToken(context: Context) {
        sharedPref = PreferenceHelper(context)
        sharedPref.delete(AUTH_TOKEN)
    }

}