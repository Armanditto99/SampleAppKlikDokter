package com.example.sampleappkd.repository

import com.example.sampleappkd.api.APICaller
import com.example.sampleappkd.model.RegisterRequest

class RegisterRepository {

    private val apiCaller by lazy { APICaller() }
    suspend fun register(request: RegisterRequest) = apiCaller.register(request)

}