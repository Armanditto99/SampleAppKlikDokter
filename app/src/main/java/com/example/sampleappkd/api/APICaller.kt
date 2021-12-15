package com.example.sampleappkd.api

import com.example.sampleappkd.base.network.BaseAPICaller
import com.example.sampleappkd.model.Doctor
import com.example.sampleappkd.model.LoginRequest
import com.example.sampleappkd.model.LoginResponse
import com.example.sampleappkd.util.Constants.Companion.ALT_BASE_URL
import com.example.sampleappkd.util.Constants.Companion.BASE_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class APICaller : BaseAPICaller(), APIInterface {

    override suspend fun getDoctorList(): Response<List<Doctor>> {
        return getInterface(APIInterface::class.java, BASE_URL).getDoctorList()
    }

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return getInterface(APIInterface::class.java, ALT_BASE_URL).login(request)
    }
}

interface APIInterface {

    @GET("api/v1/doctors")
    suspend fun getDoctorList(): Response<List<Doctor>>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

}