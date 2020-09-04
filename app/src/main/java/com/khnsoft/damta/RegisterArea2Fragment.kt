package com.khnsoft.damta

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_register_area2.*

class RegisterArea2Fragment : Fragment() {
    var x : Double? = null
    var y : Double? = null

    companion object {
        var frag: RegisterArea2Fragment? = null

        fun getInstance() : RegisterArea2Fragment {
            if (frag == null) {
                frag = RegisterArea2Fragment().apply { arguments = Bundle() }
            }
            return frag!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_register_area2, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_address.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivityForResult(intent, SearchActivity.RC_ADDRESS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SearchActivity.RC_ADDRESS -> {
                val address = data?.getStringExtra(SearchActivity.EXTRA_ADDRESS) ?: return
                et_address.setText(address)
                x = data.getDoubleExtra(SearchActivity.EXTRA_X, 0.0)
                y = data.getDoubleExtra(SearchActivity.EXTRA_Y, 0.0)
            }
        }
    }

    fun validateInformation() : Boolean {
        if (et_address.text.isBlank() || x == null || y == null) {
            return false
        }
        return true
    }
}