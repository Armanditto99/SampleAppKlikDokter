package com.example.sampleappkd.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.R
import com.example.sampleappkd.base.AuthHelper
import com.example.sampleappkd.base.BaseFragment
import com.example.sampleappkd.listdoctor.DoctorListActivity
import com.example.sampleappkd.model.LoginRequest
import com.example.sampleappkd.model.LoginResponse
import com.example.sampleappkd.register.RegisterActivity
import com.example.sampleappkd.repository.LoginRepository
import com.example.sampleappkd.util.Resource
import com.example.sampleappkd.util.ValidationHelper
import com.example.sampleappkd.viewmodel.LoginViewModel
import com.example.sampleappkd.viewmodelfactory.LoginViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    override val viewRes = R.layout.fragment_login

    lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        btn_login.setOnClickListener {
            if(isDataValid){
                login(LoginRequest(
                    email = txt_email.text.toString().trim(),
                    password = txt_password.text.toString())
                )
            }
        }

        btn_register.setOnClickListener {
            context?.let { ctx -> RegisterActivity.launchIntent(ctx) }
            activity?.finish()
        }
    }

    private fun login(request: LoginRequest) {
        viewModel.login(request)
    }

    private fun initViewModel() {
        val loginRepository = LoginRepository()
        val viewModelProviderFactory = LoginViewModelProviderFactory(loginRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)

        viewModel.data.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> onLoginLoading()
                is Resource.Success -> response.data?.let { onLoginSuccess(it) }
                is Resource.Error -> onLoginFailure()
            }
        })
    }

    private fun onLoginLoading() {

    }

    private fun onLoginSuccess(response: LoginResponse) {
        response.token?.let { AuthHelper.saveAuthToken(requireContext(), it) }
        Toast.makeText(requireContext(), "Log In Success", Toast.LENGTH_SHORT).show()
        DoctorListActivity.launchIntent(requireContext())
        activity?.finish()
    }

    private fun onLoginFailure() {
        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
    }

    private val isDataValid: Boolean
        get() {
            clearEditTextError()
            var isValid = true

            if (txt_email.text.isNullOrBlank()) {
                txt_email.requestFocus()
                txt_email_layout.error = "Field is Required"
                isValid = false
            }

            if (txt_password.text.isNullOrBlank()) {
                txt_password.requestFocus()
                txt_password_layout.error = "Field is Required"
                isValid = false
            }

            if (!ValidationHelper.isValidEmail(txt_email.text.toString())){
                txt_email.requestFocus()
                txt_email_layout.error = "Input the Valid Email"
                isValid = false
            }

            return isValid
        }

    private fun clearEditTextError() {
        txt_email_layout.isErrorEnabled = false
        txt_password_layout.isErrorEnabled = false
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}