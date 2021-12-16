package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.RegisterRequest
import com.example.sampleappkd.model.RegisterResponse
import com.example.sampleappkd.network.NetworkService
import com.example.sampleappkd.repository.RegisterRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class RegisterViewModel (val registerRepository: RegisterRepository) : ViewModel() {

    val data: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    fun register(request: RegisterRequest) = viewModelScope.launch {
        registerCall(request)
    }

    private suspend fun registerCall(request: RegisterRequest) {
        data.postValue(Resource.Loading())
        try {
            if(NetworkService.instance.isOnline()) {
                val response = registerRepository.register(request)
                data.postValue(handleRegisterResponse(response))
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

    private fun handleRegisterResponse(response: Response<RegisterResponse>) : Resource<RegisterResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}