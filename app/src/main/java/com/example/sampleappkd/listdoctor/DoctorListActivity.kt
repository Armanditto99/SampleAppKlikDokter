package com.example.sampleappkd.listdoctor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.R
import com.example.sampleappkd.base.BaseActivity
import com.example.sampleappkd.repository.DoctorRepository
import com.example.sampleappkd.viewmodel.DoctorViewModel
import com.example.sampleappkd.viewmodelfactory.DoctorViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_list_doctor.*

class DoctorListActivity : BaseActivity() {

    override val viewRes = R.layout.activity_list_doctor

    private val fragment by lazy { DoctorListFragment.newInstance() }

    lateinit var viewModel: DoctorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val doctorRepository = DoctorRepository()
        val viewModelProviderFactory = DoctorViewModelProviderFactory(doctorRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DoctorViewModel::class.java)

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(layout_fragment.id, fragment)
            .commit()
    }

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, DoctorListActivity::class.java)
            context.startActivity(intent)
        }
    }
}