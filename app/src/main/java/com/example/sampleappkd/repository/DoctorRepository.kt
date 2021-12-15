package com.example.sampleappkd.repository

import com.example.sampleappkd.api.APICaller

class DoctorRepository {

    private val apiCaller by lazy { APICaller() }
    suspend fun getDoctorList() = apiCaller.getDoctorList()

}