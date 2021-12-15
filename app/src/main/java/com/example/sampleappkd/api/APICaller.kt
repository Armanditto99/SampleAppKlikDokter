package com.example.sampleappkd.api

import com.example.sampleappkd.model.Doctor
import retrofit2.Response
import retrofit2.http.GET

interface APICaller {

    @GET("api/v1/doctors")
    suspend fun getDoctorList(): Response<List<Doctor>>

}