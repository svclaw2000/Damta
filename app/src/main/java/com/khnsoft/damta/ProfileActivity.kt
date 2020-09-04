package com.khnsoft.damta

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import com.khnsoft.damta.data.User
import com.khnsoft.damta.utils.SDF
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private var isEditting = false
    private val birthCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btn_back.setOnClickListener {
            finish()
        }

        tv_hello.text = String.format(getString(R.string.my_profile_title), User.current?.nickname)

        et_nickname.setText(User.current?.nickname)
        birthCalendar.time = User.current?.birthday
        et_birthday.setText(SDF.dateDot.format(birthCalendar.time))
        et_email.setText(User.current?.email)

        et_birthday.setOnClickListener {
            DatePickerDialog(this@ProfileActivity, { view, year, month, dayOfMonth ->
                birthCalendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                et_birthday.setText(SDF.dateDot.format(birthCalendar.time))
            }, birthCalendar[Calendar.YEAR], birthCalendar[Calendar.MONTH], birthCalendar[Calendar.DAY_OF_MONTH]).show()
        }

        btn_edit.setOnClickListener {
            et_nickname.isEnabled = !isEditting
            et_birthday.isEnabled = !isEditting
            et_email.isEnabled = !isEditting

            if (!isEditting) {
                btn_edit.text = getString(R.string.edit_profile2)
                btn_edit.background.setTint(getColor(R.color.highlight))
                isEditting = !isEditting
            } else {
                val user = User.current?.copy() ?: return@setOnClickListener
                user.nickname = et_nickname.text.toString()
                user.birthday = birthCalendar.time
                user.email = et_email.text.toString()

                if (user.save(this@ProfileActivity)) {
                    User.current = user
                    tv_hello.text = String.format(getString(R.string.my_profile_title), User.current?.nickname)
                    btn_edit.text = getString(R.string.edit_profile)
                    btn_edit.background.setTint(getColor(R.color.dark))
                    isEditting = !isEditting
                }
            }
        }
    }
}