package com.example.sampleappkd.listdoctor

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleappkd.R
import com.example.sampleappkd.adapter.recycler.DoctorItemAdapter
import com.example.sampleappkd.base.AuthHelper
import com.example.sampleappkd.base.BaseFragment
import com.example.sampleappkd.util.Resource
import com.example.sampleappkd.viewmodel.DoctorViewModel
import kotlinx.android.synthetic.main.fragment_list_doctor.*

class DoctorListFragment : BaseFragment() {

    override val viewRes = R.layout.fragment_list_doctor

    lateinit var viewModel: DoctorViewModel
    lateinit var doctorItemAdapter: DoctorItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as DoctorListActivity).viewModel
        viewModel.getDoctorList()
        setupRecyclerView()

        viewModel.doctors.observe(viewLifecycleOwner, { response ->
            when(response) {
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
                    Toast.makeText(context, "Loading ${AuthHelper.getAuthToken()}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        doctorItemAdapter = DoctorItemAdapter()
        recycler_doctor.apply {
            adapter = doctorItemAdapter
            layoutManager = LinearLayoutManager(activity)
        }
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

