package com.khnsoft.damta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_area_warning.*

class RegisterAreaWarningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_area_warning)

        btn_standard.setOnClickListener {
            val intent = Intent(this@RegisterAreaWarningActivity, CheckAreaStandardActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finish()
        }

        btn_next.setOnClickListener {
            var unchecked = 0
            var checkedNo = 0

            when (rg_question_1.checkedRadioButtonId) {
                R.id.btn_no_1 -> checkedNo ++
                -1 -> unchecked ++
            }

            when (rg_question_2.checkedRadioButtonId) {
                R.id.btn_no_2 -> checkedNo ++
                -1 -> unchecked ++
            }

            when (rg_question_3.checkedRadioButtonId) {
                R.id.btn_no_3 -> checkedNo ++
                -1 -> unchecked ++
            }

            if (unchecked > 0) {
                Toast.makeText(this@RegisterAreaWarningActivity, R.string.check_all_questions, Toast.LENGTH_LONG).show()
            } else if (checkedNo > 0) {
                val intent = Intent(this@RegisterAreaWarningActivity, CheckAreaStandardActivity::class.java)
                intent.putExtra(CheckAreaStandardActivity.EXTRA_WARNING, true)
                startActivity(intent)
            } else {
                val intent = Intent(this@RegisterAreaWarningActivity, RegisterAreaActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}