package com.example.sampleappkd.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sampleappkd.R
import com.example.sampleappkd.base.BaseActivity
import com.example.sampleappkd.util.extension.setNavigationAsBack
import com.example.sampleappkd.viewmodel.DoctorViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override val viewRes = R.layout.activity_login

    private val fragment by lazy { LoginFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = "Login"
        toolbar.setNavigationAsBack(this)

//        val doctorRepository = DoctorRepository()
//        val viewModelProviderFactory = DoctorViewModelProviderFactory(doctorRepository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DoctorViewModel::class.java)

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
                .replace(layout_fragment.id, fragment)
                .commit()
    }

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}