package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.Doctor
import com.example.sampleappkd.repository.DoctorRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DoctorViewModel (val doctorRepository: DoctorRepository) : ViewModel() {

    val doctors: MutableLiveData<Resource<List<Doctor>>> = MutableLiveData()

    fun getDoctorList() = viewModelScope.launch {
        doctors.postValue(Resource.Loading())
        val response = doctorRepository.getDoctorList()
        doctors.postValue(handleDoctorsResponse(response))
    }

    private fun handleDoctorsResponse(response: Response<List<Doctor>>) : Resource<List<Doctor>> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }

        }
        return Resource.Error(response.message())
    }
}