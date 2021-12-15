package com.example.sampleappkd.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.repository.DoctorRepository
import com.example.sampleappkd.viewmodel.DoctorViewModel

class DoctorViewModelProviderFactory (val doctorRepository: DoctorRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DoctorViewModel(doctorRepository) as T
    }

}