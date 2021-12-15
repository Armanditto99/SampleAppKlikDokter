package com.example.sampleappkd.util.extension

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import com.example.sampleappkd.R

fun Toolbar.setNavigationAsBack(activity: Activity, backListener: (() -> Unit)? = null) {
    setNavigationIcon(R.drawable.ic_back_button)
    setNavigationOnClickListener {
        if (backListener != null) backListener.invoke()
        else activity.onBackPressed()
    }
}