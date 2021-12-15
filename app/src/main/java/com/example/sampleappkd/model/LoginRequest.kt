package com.example.sampleappkd.model

import com.google.gson.annotations.SerializedName

class LoginRequest() {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    constructor(email: String, password: String) : this() {
        this.email = email
        this.password = password
    }

}