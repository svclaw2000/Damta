package com.khnsoft.damta

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.khnsoft.damta.data.User
import com.khnsoft.damta.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.popup_warning
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.popup_warning.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sp = SharedPreferencesHelper.getSharedPreferences(this@LoginActivity)
        val editor = sp.edit()

        btn_login.setOnClickListener {
            val user = User.getByUsername(this@LoginActivity, et_username.text.toString())

            if (user != null && user.password == et_password.text.toString()) {
                User.current = user

                if (cb_save_username.isChecked) {
                    editor.putString(SharedPreferencesHelper.SP_USERNAME, user.username)
                } else {
                    editor.remove(SharedPreferencesHelper.SP_USERNAME)
                }

                if (cb_auto_login.isChecked) {
                    editor.putInt(SharedPreferencesHelper.SP_USER_ID, user.id)
                } else {
                    editor.remove(SharedPreferencesHelper.SP_USER_ID)
                }

                editor.commit()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                popupWarning(getString(R.string.warning_login_failed))
            }
        }

        btn_register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_confirm.setOnClickListener {
            removeWarning()
        }

        val autoLoginId = sp.getInt(SharedPreferencesHelper.SP_USER_ID, -1)
        if (autoLoginId != -1) {
            cb_auto_login.isChecked = true
            val user = User.getById(this@LoginActivity, autoLoginId)
            if (user != null) {
                User.current = user
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, getString(R.string.warning_login_failed), Toast.LENGTH_LONG).show()
            }
        }
        val savedUsername = sp.getString(SharedPreferencesHelper.SP_USERNAME, null)
        if (savedUsername != null) {
            et_username.setText(savedUsername)
            cb_save_username.isChecked = true
        }
    }

    private fun popupWarning(msg: String) {
        tv_warning.text = msg
        popup_warning.visibility = View.VISIBLE
    }

    private fun removeWarning() {
        popup_warning.visibility = View.GONE
    }
}