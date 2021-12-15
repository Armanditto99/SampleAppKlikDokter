package com.example.sampleappkd.repository

import com.example.sampleappkd.api.APICaller
import com.example.sampleappkd.model.LoginRequest

class LoginRepository {

    private val apiCaller by lazy { APICaller() }
    suspend fun login(request: LoginRequest) = apiCaller.login(request)

}