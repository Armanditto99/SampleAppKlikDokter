package com.example.sampleappkd.util

import android.content.Context
import android.content.SharedPreferences
import com.example.sampleappkd.util.Constants.Companion.PREF_NAME

class PreferenceHelper(context: Context) {

    private var sharedpref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedpref.edit()

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun getString(key: String?) :String? {
        return sharedpref.getString(key, null)
    }

    fun delete(key: String?) {
        editor.remove(key).apply()
    }
}