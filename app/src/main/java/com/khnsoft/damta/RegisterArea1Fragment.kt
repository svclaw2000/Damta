package com.khnsoft.damta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_register_area1.*

class RegisterArea1Fragment : Fragment() {
    companion object {
        var frag: RegisterArea1Fragment? = null

        fun getInstance() : RegisterArea1Fragment {
            if (frag == null) {
                frag = RegisterArea1Fragment().apply { arguments = Bundle() }
            }
            return frag!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_register_area1, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun validateInformation() : Boolean {
        if (et_name.text.isBlank()) {
            return false
        }
        return true
    }
}