package com.example.sampleappkd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sampleappkd.listdoctor.DoctorListActivity
import com.orhanobut.hawk.Hawk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Hawk.init(this).build()

        DoctorListActivity.launchIntent(this)
        finish()
    }
}