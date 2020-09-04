package com.khnsoft.damta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_check_area_standard.*

class CheckAreaStandardActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_WARNING = "standard_warning"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_area_standard)

        if (intent.getBooleanExtra(EXTRA_WARNING, false)) {
            tv_standard_title.text = getString(R.string.register_standard_warning)
        }

        btn_finish.setOnClickListener {
            finish()
        }
    }
}