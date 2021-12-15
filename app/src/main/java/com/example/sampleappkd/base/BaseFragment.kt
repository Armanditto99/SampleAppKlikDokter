package com.example.sampleappkd.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val viewRes: Int?

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val res = viewRes
        return if (res == null) null
        else inflater.inflate(res, container, false)
    }

}