package com.example.sampleappkd.addnewdoctor

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.R
import com.example.sampleappkd.base.AuthHelper
import com.example.sampleappkd.base.BaseFragment
import com.example.sampleappkd.listdoctor.DoctorListActivity
import com.example.sampleappkd.login.LoginActivity
import com.example.sampleappkd.model.AddDoctorRequest
import com.example.sampleappkd.model.AddDoctorResponse
import com.example.sampleappkd.repository.AddDoctorRepository
import com.example.sampleappkd.util.Resource
import com.example.sampleappkd.viewmodel.AddDoctorViewModel
import com.example.sampleappkd.viewmodelfactory.AddDoctorViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_add_doctor.*

class AddDoctorFragment : BaseFragment() {

    override val viewRes = R.layout.fragment_add_doctor

    lateinit var viewModel: AddDoctorViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        btn_add.setOnClickListener {
            if(isDataValid){
                addDoctor(
                    AddDoctorRequest(
                        doctor = txt_name.text.toString().trim(),
                        consultationPrice = txt_fee.text.toString().trim(),
                        btcAddress = txt_bitcoin_address.text.toString().trim()
                    )
                )
            }
        }
    }

    private fun addDoctor(request: AddDoctorRequest) {
        viewModel.addDoctor(request)
    }

    private fun initViewModel() {
        val addDoctorRepository = AddDoctorRepository()
        val viewModelProviderFactory = AddDoctorViewModelProviderFactory(addDoctorRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(AddDoctorViewModel::class.java)

        viewModel.data.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> onAddDoctorLoading()
                is Resource.Success -> response.data?.let { onAddDoctorSuccess(it) }
                is Resource.Error -> onAddDoctorFailure()
            }
        })
    }

    private fun onAddDoctorLoading() {
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
    }

    private fun onAddDoctorSuccess(response: AddDoctorResponse) {
        Toast.makeText(requireContext(), "${response.doctor} Added", Toast.LENGTH_LONG).show()
        DoctorListActivity.launchIntent(requireContext())
        activity?.finish()
    }

    private fun onAddDoctorFailure() {
        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
    }

    private val isDataValid: Boolean
        get() {
            clearEditTextError()
            var isValid = true

            if (txt_name.text.isNullOrBlank()) {
                txt_name.requestFocus()
                txt_name_layout.error = "Field is Required"
                isValid = false
            }

            if (txt_fee.text.isNullOrBlank()) {
                txt_fee.requestFocus()
                txt_fee_layout.error = "Field is Required"
                isValid = false
            }

            if (txt_bitcoin_address.text.isNullOrBlank()) {
                txt_bitcoin_address.requestFocus()
                txt_bitcoin_address_layout.error = "Field is Required"
                isValid = false
            }

            return isValid
        }

    private fun clearEditTextError() {
        txt_name_layout.isErrorEnabled = false
        txt_fee_layout.isErrorEnabled = false
        txt_bitcoin_address_layout.isErrorEnabled = false
    }

    companion object {
        fun newInstance(): AddDoctorFragment {
            return AddDoctorFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}