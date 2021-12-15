package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.DeleteDoctorResponse
import com.example.sampleappkd.repository.DeleteDoctorRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DeleteDoctorViewModel (val deleteDoctorRepository: DeleteDoctorRepository) : ViewModel() {

    val data: MutableLiveData<Resource<DeleteDoctorResponse>> = MutableLiveData()

    fun deleteDoctor(id: String?) = viewModelScope.launch {
        data.postValue(Resource.Loading())
        val response = deleteDoctorRepository.deleteDoctor(id)
        data.postValue(handleDeleteDoctorResponse(response))
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