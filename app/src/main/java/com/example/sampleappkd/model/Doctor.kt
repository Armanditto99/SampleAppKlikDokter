package com.example.sampleappkd.model

import com.google.gson.annotations.SerializedName

class Doctor {

    @SerializedName("createdAt")
    var createdAt: String? = null

    @SerializedName("doctor")
    var doctor: String? = null

    @SerializedName("avatar")
    var avatar: String? = null

    @SerializedName("available_until")
    var availableUntil: String? = null

    @SerializedName("consultation_price")
    var consultationPrice: String? = null

    @SerializedName("btc_address")
    var btcAddress: String? = null

    @SerializedName("id")
    var id: String? = null

    var isExpanded: Boolean = false
}