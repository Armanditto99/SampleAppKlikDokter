package com.example.sampleappkd.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.R
import com.example.sampleappkd.base.BaseFragment
import com.example.sampleappkd.login.LoginActivity
import com.example.sampleappkd.model.RegisterRequest
import com.example.sampleappkd.model.RegisterResponse
import com.example.sampleappkd.repository.RegisterRepository
import com.example.sampleappkd.util.Resource
import com.example.sampleappkd.viewmodel.RegisterViewModel
import com.example.sampleappkd.viewmodelfactory.RegisterViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {

    override val viewRes = R.layout.fragment_register

    lateinit var viewModel: RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        btn_register.setOnClickListener {
            if(isDataValid){
                register(
                    RegisterRequest(
                        email = txt_email.text.toString().trim(),
                        password = txt_password.text.toString()
                    )
                )
            }
        }

        btn_login.setOnClickListener {
            context?.let { ctx -> LoginActivity.launchIntent(ctx) }
            activity?.finish()
        }
    }

    private fun register(request: RegisterRequest) {
        viewModel.register(request)
    }

    private fun initViewModel() {
        val registerRepository = RegisterRepository()
        val viewModelProviderFactory = RegisterViewModelProviderFactory(registerRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(RegisterViewModel::class.java)

        viewModel.data.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> onRegisterLoading()
                is Resource.Success -> response.data?.let { onRegisterSuccess(it) }
                is Resource.Error -> onRegisterFailure()
            }
        })
    }

    private fun onRegisterLoading() {
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
    }

    private fun onRegisterSuccess(response: RegisterResponse) {
        Toast.makeText(requireContext(), "Success ${response.data?.email}", Toast.LENGTH_LONG).show()
        LoginActivity.launchIntent(requireContext())
        activity?.finish()
    }

    private fun onRegisterFailure() {
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

            if (txt_password_confirmation.text.isNullOrBlank()) {
                txt_password_confirmation.requestFocus()
                txt_password_confirmation_layout.error = "Field is Required"
                isValid = false
            }

            if (!txt_password.text.isNullOrBlank() && !txt_password_confirmation.text.isNullOrBlank()) {
                if (txt_password_confirmation.text.toString() != txt_password.text.toString()) {
                    txt_password_confirmation.requestFocus()
                    txt_password_confirmation_layout.error = "Not match"
                    txt_password_layout.error = "Not match"
                    isValid = false
                }
            }

            return isValid
        }

    private fun clearEditTextError() {
        txt_email_layout.isErrorEnabled = false
        txt_password_layout.isErrorEnabled = false
        txt_password_confirmation_layout.isErrorEnabled = false
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}