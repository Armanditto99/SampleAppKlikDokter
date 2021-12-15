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
import com.example.sampleappkd.repository.LoginRepository
import com.example.sampleappkd.util.Resource
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
            login(LoginRequest(
                    email = txt_email.text.toString().trim(),
                    password = txt_password.text.toString())
            )
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
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
    }

    private fun onLoginSuccess(response: LoginResponse) {
        response.token?.let { AuthHelper.saveAuthToken(it) }
        DoctorListActivity.launchIntent(requireContext())
        activity?.finish()
    }

    private fun onLoginFailure() {
        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
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