package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.LoginRequest
import com.example.sampleappkd.model.LoginResponse
import com.example.sampleappkd.network.NetworkService
import com.example.sampleappkd.repository.LoginRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class LoginViewModel (val loginRepository: LoginRepository) : ViewModel() {

    val data: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    fun login(request: LoginRequest) = viewModelScope.launch {
        loginCall(request)
    }

    private suspend fun loginCall(request: LoginRequest) {
        data.postValue(Resource.Loading())
        try {
            if(NetworkService.instance.isOnline()) {
                val response = loginRepository.login(request)
                data.postValue(handleLoginResponse(response))
            } else {
                data.postValue(Resource.Error("No Internet Connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> data.postValue(Resource.Error("Network Failure"))
                else -> data.postValue(Resource.Error("An Error Occurred"))
            }
        }
    }

    private fun handleLoginResponse(response: Response<LoginResponse>) : Resource<LoginResponse> {
        if(NetworkService.instance.isOnline()) {
            if(response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
        }
        return Resource.Error(response.message())
    }
}