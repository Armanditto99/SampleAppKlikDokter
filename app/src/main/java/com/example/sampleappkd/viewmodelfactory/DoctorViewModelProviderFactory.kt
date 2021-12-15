package com.example.sampleappkd.viewmodelfactory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sampleappkd.model.Doctor
import com.example.sampleappkd.repository.DoctorRepository
import com.example.sampleappkd.util.Resource
import com.example.sampleappkd.viewmodel.DoctorViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class DoctorViewModelProviderFactory (val doctorRepository: DoctorRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DoctorViewModel(doctorRepository) as T
    }

}