package com.example.sampleappkd.model

import com.google.gson.annotations.SerializedName

class AddDoctorRequest() {

    @SerializedName("doctor")
    var doctor: String? = null

    @SerializedName("consultation_price")
    var consultationPrice: String? = null

    @SerializedName("btc_address")
    var btcAddress: String? = null

    constructor(doctor: String, consultationPrice: String, btcAddress: String) : this() {
        this.doctor = doctor
        this.consultationPrice = consultationPrice
        this.btcAddress = btcAddress
    }
}