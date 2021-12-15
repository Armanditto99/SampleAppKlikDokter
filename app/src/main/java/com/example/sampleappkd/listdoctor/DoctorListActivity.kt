package com.example.sampleappkd.listdoctor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappkd.R
import com.example.sampleappkd.base.BaseActivity
import com.example.sampleappkd.login.LoginActivity
import com.example.sampleappkd.repository.DoctorRepository
import com.example.sampleappkd.viewmodel.DoctorViewModel
import com.example.sampleappkd.viewmodelfactory.DoctorViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_list_doctor.*
import kotlinx.android.synthetic.main.activity_list_doctor.view.*
import kotlinx.android.synthetic.main.header_toolbar_menu.view.*

class DoctorListActivity : BaseActivity() {

    override val viewRes = R.layout.activity_list_doctor

    private val fragment by lazy { DoctorListFragment.newInstance() }

    lateinit var viewModel: DoctorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val doctorRepository = DoctorRepository()
        val viewModelProviderFactory = DoctorViewModelProviderFactory(doctorRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DoctorViewModel::class.java)

        toolbar.header_toolbar.img_login.setOnClickListener {
            LoginActivity.launchIntent(this)
        }

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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}