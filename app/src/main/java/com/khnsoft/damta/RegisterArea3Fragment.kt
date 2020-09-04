package com.khnsoft.damta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class RegisterArea3Fragment : Fragment() {
    companion object {
        var frag: RegisterArea3Fragment? = null

        fun getInstance() : RegisterArea3Fragment {
            if (frag == null) {
                frag = RegisterArea3Fragment().apply { arguments = Bundle() }
            }
            return frag!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_register_area3, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun validateInformation() : Boolean {
        return true
    }
}