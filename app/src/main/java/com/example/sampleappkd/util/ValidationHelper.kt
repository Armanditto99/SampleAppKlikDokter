package com.example.sampleappkd.util

import androidx.core.util.PatternsCompat

object ValidationHelper  {

    fun isValidEmail(text: String?): Boolean {
        if (text.isNullOrEmpty()) return false
        return PatternsCompat.EMAIL_ADDRESS.matcher(text).matches()
    }

}