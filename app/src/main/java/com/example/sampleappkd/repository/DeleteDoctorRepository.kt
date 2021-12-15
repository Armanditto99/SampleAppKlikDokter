package com.example.sampleappkd.repository

import com.example.sampleappkd.api.APICaller

class DeleteDoctorRepository {

    private val apiCaller by lazy { APICaller() }
    suspend fun deleteDoctor(id: String?) = apiCaller.deleteDoctor(id)

}