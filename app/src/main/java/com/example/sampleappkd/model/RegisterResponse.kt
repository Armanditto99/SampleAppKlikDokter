package com.example.sampleappkd.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class RegisterResponse {

    @SerializedName("data")
    var data : Data? = null

    class Data {
        @SerializedName("email")
        val email: String? = null

        @SerializedName("updated_at")
        val updatedAt: String? = null

        @SerializedName("created_at")
        val createdAt: String? = null

        @SerializedName("id")
        val id: Int? = null
    }

}