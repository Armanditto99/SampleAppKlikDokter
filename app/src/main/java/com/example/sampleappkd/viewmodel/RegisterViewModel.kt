package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.LoginRequest
import com.example.sampleappkd.model.LoginResponse
import com.example.sampleappkd.model.RegisterRequest
import com.example.sampleappkd.model.RegisterResponse
import com.example.sampleappkd.repository.RegisterRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel (val registerRepository: RegisterRepository) : ViewModel() {

    val data: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    fun register(request: RegisterRequest) = viewModelScope.launch {
        data.postValue(Resource.Loading())
        val response = registerRepository.register(request)
        data.postValue(handleRegisterResponse(response))
    }

    private fun handleRegisterResponse(response: Response<RegisterResponse>) : Resource<RegisterResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}