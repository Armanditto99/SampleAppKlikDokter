package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.Doctor
import com.example.sampleappkd.model.LoginRequest
import com.example.sampleappkd.model.LoginResponse
import com.example.sampleappkd.repository.LoginRepository
import com.example.sampleappkd.util.Constants.Companion.AUTH_TOKEN
import com.example.sampleappkd.util.Resource
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel (val loginRepository: LoginRepository) : ViewModel() {

    val data: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    fun login(request: LoginRequest) = viewModelScope.launch {
        data.postValue(Resource.Loading())
        val response = loginRepository.login(request)
        data.postValue(handleLoginResponse(response))
    }

    private fun handleLoginResponse(response: Response<LoginResponse>) : Resource<LoginResponse> {
        if(response.isSuccessful) {
            Hawk.put(AUTH_TOKEN, response.body()?.token)

            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}