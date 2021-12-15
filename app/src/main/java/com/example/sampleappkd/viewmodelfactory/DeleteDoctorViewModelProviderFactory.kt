package com.example.sampleappkd.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.repository.DeleteDoctorRepository
import com.example.sampleappkd.viewmodel.DeleteDoctorViewModel

class DeleteDoctorViewModelProviderFactory (val deleteDoctorRepository: DeleteDoctorRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeleteDoctorViewModel(deleteDoctorRepository) as T
    }

}