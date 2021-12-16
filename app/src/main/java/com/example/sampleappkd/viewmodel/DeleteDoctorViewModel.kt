package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.DeleteDoctorResponse
import com.example.sampleappkd.network.NetworkService
import com.example.sampleappkd.repository.DeleteDoctorRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class DeleteDoctorViewModel (val deleteDoctorRepository: DeleteDoctorRepository) : ViewModel() {

    val data: MutableLiveData<Resource<DeleteDoctorResponse>> = MutableLiveData()

    fun deleteDoctor(id: String?) = viewModelScope.launch {
        deleteDoctorCall(id)
    }

    private suspend fun deleteDoctorCall(id: String?) {
        data.postValue(Resource.Loading())
        try {
            if(NetworkService.instance.isOnline()) {
                val response = deleteDoctorRepository.deleteDoctor(id)
                data.postValue(handleDeleteDoctorResponse(response))
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

    private fun handleDeleteDoctorResponse(response: Response<DeleteDoctorResponse>) : Resource<DeleteDoctorResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}