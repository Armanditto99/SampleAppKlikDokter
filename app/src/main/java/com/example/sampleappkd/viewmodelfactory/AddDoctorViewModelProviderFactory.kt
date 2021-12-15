package com.example.sampleappkd.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.repository.AddDoctorRepository
import com.example.sampleappkd.viewmodel.AddDoctorViewModel

class AddDoctorViewModelProviderFactory (val addDoctorRepository: AddDoctorRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddDoctorViewModel(addDoctorRepository) as T
    }

}