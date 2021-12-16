package com.example.sampleappkd

import android.app.Application
import com.example.sampleappkd.network.NetworkService

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupServices()
    }

    private fun setupServices() {
        NetworkService.instance.initializeWithApplicationContext(this)
    }

}