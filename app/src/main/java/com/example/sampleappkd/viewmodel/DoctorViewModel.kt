package com.example.sampleappkd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.Doctor
import com.example.sampleappkd.network.NetworkService
import com.example.sampleappkd.repository.DoctorRepository
import com.example.sampleappkd.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class DoctorViewModel (val doctorRepository: DoctorRepository) : ViewModel() {

    val doctors: MutableLiveData<Resource<List<Doctor>>> = MutableLiveData()

    fun getDoctorList() = viewModelScope.launch {
        getDoctorListCall()
    }

    private suspend fun getDoctorListCall() {
        doctors.postValue(Resource.Loading())
        try {
            if(NetworkService.instance.isOnline()) {
                val response = doctorRepository.getDoctorList()
                doctors.postValue(handleDoctorsResponse(response))
            } else {
                doctors.postValue(Resource.Error("No Internet Connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> doctors.postValue(Resource.Error("Network Failure"))
                else -> doctors.postValue(Resource.Error("An Error Occurred"))
            }
        }
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