package com.example.sampleappkd.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.repository.RegisterRepository
import com.example.sampleappkd.viewmodel.RegisterViewModel

class RegisterViewModelProviderFactory (val registerRepository: RegisterRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(registerRepository) as T
    }

}