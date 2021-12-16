package com.example.sampleappkd.listdoctor

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleappkd.R
import com.example.sampleappkd.adapter.recycler.DoctorItemAdapter
import com.example.sampleappkd.addnewdoctor.AddDoctorActivity
import com.example.sampleappkd.base.AuthHelper
import com.example.sampleappkd.base.BaseFragment
import com.example.sampleappkd.model.DeleteDoctorResponse
import com.example.sampleappkd.repository.DeleteDoctorRepository
import com.example.sampleappkd.util.Resource
import com.example.sampleappkd.viewmodel.DeleteDoctorViewModel
import com.example.sampleappkd.viewmodel.DoctorViewModel
import com.example.sampleappkd.viewmodelfactory.DeleteDoctorViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_list_doctor.*


class DoctorListFragment : BaseFragment() {

    override val viewRes = R.layout.fragment_list_doctor

    private lateinit var viewModel: DoctorViewModel
    lateinit var deleteViewModel: DeleteDoctorViewModel
    lateinit var doctorItemAdapter: DoctorItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as DoctorListActivity).viewModel
        viewModel.getDoctorList()
        setupRecyclerView()

        viewModel.doctors.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { doctorsResponse ->
                        doctorItemAdapter.differ.submitList(doctorsResponse)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("DLF", "An error occured: $message")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    Toast.makeText(context, "Loading ${AuthHelper.getAuthToken(requireContext())}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        initDeleteViewModel()

        btn_add_new.setOnClickListener {
            context?.let { ctx -> AddDoctorActivity.launchIntent(ctx) }
        }

        if(AuthHelper.getAuthToken(requireContext()) == null) btn_add_new.visibility = View.GONE
        else btn_add_new.visibility = View.VISIBLE
    }

    private fun initDeleteViewModel() {
        val deleteDoctorRepository = DeleteDoctorRepository()
        val viewModelProviderFactory = DeleteDoctorViewModelProviderFactory(deleteDoctorRepository)
        deleteViewModel = ViewModelProvider(this, viewModelProviderFactory).get(DeleteDoctorViewModel::class.java)

        deleteViewModel.data.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> onDeleteDoctorLoading()
                is Resource.Success -> response.data?.let { onDeleteDoctorSuccess(it) }
                is Resource.Error -> onDeleteDoctorFailure()
            }
        })
    }

    private fun setupRecyclerView() {
        doctorItemAdapter = DoctorItemAdapter()
        recycler_doctor.apply {
            adapter = doctorItemAdapter.apply {
                onDeleteIconClickListener = { doctor ->
                    AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("Are you sure you want to delete ${doctor.doctor} ?")
                            .setPositiveButton("Yes") { _, _ ->
                                deleteViewModel.deleteDoctor(doctor.id)
                                DoctorListActivity.launchIntent(context)
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                }
            }
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onDeleteDoctorLoading() {
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
    }

    private fun onDeleteDoctorSuccess(response: DeleteDoctorResponse) {
        Toast.makeText(requireContext(), "${response.doctor} Deleted", Toast.LENGTH_LONG).show()
        DoctorListActivity.launchIntent(requireContext())
        activity?.finish()
    }

    private fun onDeleteDoctorFailure() {
        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(): DoctorListFragment {
            return DoctorListFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}

