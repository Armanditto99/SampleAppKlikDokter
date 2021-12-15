package com.example.sampleappkd.repository

import com.example.sampleappkd.api.APICaller
import com.example.sampleappkd.model.AddDoctorRequest

class AddDoctorRepository {

    private val apiCaller by lazy { APICaller() }
    suspend fun addDoctor(request: AddDoctorRequest) = apiCaller.addDoctor(request)

}