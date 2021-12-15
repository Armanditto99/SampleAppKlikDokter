package com.example.sampleappkd.repository

import com.example.sampleappkd.api.RetrofitInstance

class DoctorRepository {

    suspend fun getDoctorList() = RetrofitInstance.api.getDoctorList()

}