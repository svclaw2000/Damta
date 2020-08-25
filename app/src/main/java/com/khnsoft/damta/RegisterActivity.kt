package com.khnsoft.damta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.khnsoft.damta.data.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.frag_register1.*
import kotlinx.android.synthetic.main.frag_register2.*
import kotlinx.android.synthetic.main.popup_warning.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    var cur_frag = 1
    val frag1 = Register1Fragment.getInstance()
    val frag2 = Register2Fragment.getInstance()

    val today = Calendar.getInstance()

    companion object {
        val curUser = User()
        var birthCalendar : Calendar? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_previous.setOnClickListener {
            when (cur_frag) {
                1 -> {
                    finish()
                }
                2 -> {
                    callPage(1)
                }
            }
        }

        btn_next.setOnClickListener {
            when (cur_frag) {
                1 -> {
                    if (et_username.text.toString().isBlank() || et_password.text.toString().isBlank()) {
                        popupWarning(getString(R.string.warning_empty_field))
                    } else if (User.hasSameUsername(this@RegisterActivity, frag1.et_username.text.toString())) {
                        popupWarning(getString(R.string.warning_same_username))
                    } else {
                        curUser.username = et_username.text.toString()
                        curUser.password = et_password.text.toString()
                        callPage(2)
                    }
                }
                2 -> {
                    if (et_nickname.text.toString().isBlank() || et_email.text.toString().isBlank() || birthCalendar == null) {
                        popupWarning(getString(R.string.warning_empty_field))
                    } else if (today[Calendar.YEAR] - birthCalendar!![Calendar.YEAR] + 1 < 20) {
                        popupWarning(getString(R.string.warning_age))
                    } else {
                        curUser.nickname = et_nickname.text.toString()
                        curUser.birthday = birthCalendar!!.time
                        curUser.email = et_email.text.toString()

                        val result = curUser.add(this@RegisterActivity)
                        if (result) {
                            Toast.makeText(this@RegisterActivity, getString(R.string.register_success), Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, getString(R.string.error_occured), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        btn_confirm.setOnClickListener {
            removeWarning()
        }

        callPage(1)
    }

    private fun popupWarning(msg: String) {
        tv_warning.text = msg
        popup_warning.visibility = View.VISIBLE
    }

    private fun removeWarning() {
        popup_warning.visibility = View.GONE
    }

    private fun callPage(idx: Int) {
        val transaction = supportFragmentManager.beginTransaction()

        when (idx) {
            1 -> {
                btn_next.text = getString(R.string.next)
                btn_next.setTextColor(getColor(R.color.dark))
                transaction.replace(R.id.frag_container, Register1Fragment.getInstance())
                transaction.commit()
            }
            2 -> {
                btn_next.text = getString(R.string.finish)
                btn_next.setTextColor(getColor(R.color.highlight))
                transaction.replace(R.id.frag_container, Register2Fragment.getInstance())
                transaction.commit()
            }
        }

        cur_frag = idx
    }
}