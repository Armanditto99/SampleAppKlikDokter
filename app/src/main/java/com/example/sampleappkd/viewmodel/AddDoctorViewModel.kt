package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.AddDoctorRequest
import com.example.sampleappkd.model.AddDoctorResponse
import com.example.sampleappkd.network.NetworkService
import com.example.sampleappkd.repository.AddDoctorRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class AddDoctorViewModel (val addDoctorRepository: AddDoctorRepository) : ViewModel() {

    val data: MutableLiveData<Resource<AddDoctorResponse>> = MutableLiveData()

    fun addDoctor(request: AddDoctorRequest) = viewModelScope.launch {
        addDoctorCall(request)
    }

    private suspend fun addDoctorCall(request: AddDoctorRequest) {
        data.postValue(Resource.Loading())
        try {
            if(NetworkService.instance.isOnline()) {
                val response = addDoctorRepository.addDoctor(request)
                data.postValue(handleAddDoctorResponse(response))
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

    private fun handleAddDoctorResponse(response: Response<AddDoctorResponse>) : Resource<AddDoctorResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}