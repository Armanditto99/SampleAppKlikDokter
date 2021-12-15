package com.example.sampleappkd.base.network

import com.example.sampleappkd.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseAPICaller  {

    fun <T> getInterface(clazz: Class<T>, endpoint: String? = null): T {
        return getRetrofit(endpoint).create(clazz)
    }

    private fun getRetrofit(endpoint: String? = null): Retrofit {
        return Retrofit.Builder()
                .baseUrl(if (endpoint.isNullOrBlank()) BASE_URL else endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClientHelper().initOkHttpClient())
                .build()
    }

}