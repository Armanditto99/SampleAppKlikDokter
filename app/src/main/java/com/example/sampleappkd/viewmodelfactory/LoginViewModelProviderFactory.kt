package com.example.sampleappkd.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.repository.LoginRepository
import com.example.sampleappkd.viewmodel.LoginViewModel

class LoginViewModelProviderFactory (val loginRepository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepository) as T
    }

}